package com.example.financeservice.service.keycloak;

import com.example.financeservice.dto.auth.RegisterDTO;
import com.example.financeservice.exception.registration.RegistrationException;
import org.keycloak.representations.idm.UserRepresentation;

public interface IKeycloakAdminService {
    RegisterDTO createUser(RegisterDTO userRegistrationRecord) throws RegistrationException;

    UserRepresentation findByUsername(String username);
}
