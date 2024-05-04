package com.example.financeservice.controller.piggy;

import com.example.financeservice.dto.account.piggy.PiggyBankDTO;
import com.example.financeservice.dto.response.ResponseDTO;
import com.example.financeservice.exception.user.UserPrincipalNotFoundException;
import com.example.financeservice.mapper.piggy.PiggyBankMapper;
import com.example.financeservice.service.piggy.IPiggyService;
import com.example.financeservice.service.user.IUserService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/piggies")
@RequiredArgsConstructor
public class PiggyBankController {

    private final IPiggyService piggyService;
    private final IUserService userService;
    private final PiggyBankMapper piggyBankMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO<PiggyBankDTO> create(@RequestBody @JsonView(PiggyBankDTO.PiggyBankCreateView.class) PiggyBankDTO piggyBankDTO,
                                            Principal principal) {

        return userService.findByUsername(principal.getName())
                .map(user -> ResponseDTO.<PiggyBankDTO>builder()
                        .data(piggyBankMapper.toDTO(piggyService.create(piggyBankMapper.toModel(piggyBankDTO.setOwnerId(user.getId())
                                .setAmount(BigDecimal.valueOf(0.0))))))
                        .build())
                .orElseThrow(UserPrincipalNotFoundException::new);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<List<PiggyBankDTO>> getAllByUser(Principal principal) {

        return userService.findByUsername(principal.getName())
                .map(user -> ResponseDTO.<List<PiggyBankDTO>>builder()
                        .data(piggyService.getAllByOwnerId(user.getId()).stream().map(piggyBankMapper::toDTO).toList())
                        .build())
                .orElseThrow(UserPrincipalNotFoundException::new);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<PiggyBankDTO> update(@RequestBody @JsonView({PiggyBankDTO.PiggyBankUpdateView.class}) PiggyBankDTO piggyBankDTO,
                                            Principal principal) {
        return ResponseDTO.<PiggyBankDTO>builder()
                .data(userService.findByUsername(principal.getName())
                        .map(user -> piggyService.updateSomeField(piggyBankMapper.toModel(piggyBankDTO.setOwnerId(user.getId()))))
                        .map(piggyBankMapper::toDTO)
                        .orElseThrow(UserPrincipalNotFoundException::new))
                .build();
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestBody @JsonView({PiggyBankDTO.PiggyBankUpdateView.class}) PiggyBankDTO piggyBankDTO,
                       Principal principal) {

        userService.findByUsername(principal.getName()).ifPresent(user -> {
            piggyService.delete(piggyBankMapper.toModel(piggyBankDTO.setOwnerId(user.getId())));
        });
    }
}
