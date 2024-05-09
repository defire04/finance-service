package com.example.financeservice.controller.auth;

import com.example.financeservice.dto.auth.RegisterDTO;
import com.example.financeservice.mapper.user.UserMapper;
import com.example.financeservice.model.user.User;
import com.example.financeservice.service.auth.IAdminService;
import com.example.financeservice.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/auth/v1")
@RequiredArgsConstructor
public class AuthController {

    private final IAdminService adminService;

    private final IUserService userService;

    private final UserMapper userMapper;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterDTO register(@RequestBody RegisterDTO registerDTO) {
        return adminService.createUser(registerDTO);
    }

    @GetMapping("/sign-in")
    @ResponseStatus(HttpStatus.OK)
    public User signIn(Principal principal) {
        System.out.println(principal.getName());

        return userService.findByUsername(principal.getName())
                .orElseGet(() -> {
                    UserRepresentation representation = adminService.findByUsername(principal.getName());
                    return userService.create(userMapper.toModel(representation));
                });
    }
}
