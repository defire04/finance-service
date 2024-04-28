package com.example.financeservice.controller.auth;

import com.example.financeservice.dto.auth.RegisterDTO;
import com.example.financeservice.mapper.user.UserMapper;
import com.example.financeservice.model.user.User;
import com.example.financeservice.service.keycloak.imp.KeycloakAdminService;
import com.example.financeservice.service.user.IUserService;
import com.example.financeservice.service.user.imp.UserService;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/auth/v1")
@RequiredArgsConstructor
public class AuthController {

    private final KeycloakAdminService keycloakAdminService;

    private final IUserService userService;

    private final UserMapper userMapper;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterDTO register(@RequestBody RegisterDTO registerDTO) {
        return keycloakAdminService.createUser(registerDTO);
    }

    @GetMapping("/sign-in")
    @ResponseStatus(HttpStatus.OK)
    public User signIn(Principal principal) {
        return userService.findByUsername(principal.getName())
                .orElseGet(() -> {
                    UserRepresentation representation = keycloakAdminService.findByUsername(principal.getName());
                    return userService.create(userMapper.toModel(representation));
                });
    }
}
