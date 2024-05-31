package com.example.financeservice.controller.auth;

import com.example.financeservice.dto.auth.RegisterDTO;
import com.example.financeservice.dto.auth.SignInUserDTO;
import com.example.financeservice.dto.response.ResponseDTO;
import com.example.financeservice.mapper.user.UserMapper;
import com.example.financeservice.model.user.User;
import com.example.financeservice.service.auth.IRegisterService;
import com.example.financeservice.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/auth/v1")
@RequiredArgsConstructor
public class AuthController {

    private final IRegisterService adminService;

    private final IUserService userService;

    private final UserMapper userMapper;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO<RegisterDTO> register(@RequestBody RegisterDTO registerDTO) {
        return new ResponseDTO<>(adminService.createUser(registerDTO));
    }

    @GetMapping("/sign-in")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<SignInUserDTO> signIn(Principal principal) {
        System.out.println(principal.getName());


        return userService.findByUsername(principal.getName())
                .map(user -> new ResponseDTO<>(userMapper.toSignInDTO(user)))
                .orElseGet(() -> {
                    UserRepresentation representation = adminService.findByUsername(principal.getName());
                    return new ResponseDTO<>(userMapper.toSignInDTO(userService.create(userMapper.toModel(representation))));
                });
    }

}
