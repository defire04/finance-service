package com.example.financeservice.service.keycloak;

import com.example.financeservice.dto.auth.RegisterDTO;
import com.example.financeservice.exception.registration.RegistrationException;
import com.example.financeservice.exception.user.KeycloakUserNotFountException;
import org.keycloak.representations.idm.UserRepresentation;

public interface IKeycloakAdminService {
    /**
     * Creates a new user in Keycloak.
     *
     * @param userRegistrationRecord The user registration record to create the user
     * @return The user registration record of the created user
     * @throws RegistrationException If there is an error during user registration
     */
    RegisterDTO createUser(RegisterDTO userRegistrationRecord) throws RegistrationException;

    /**
     * Finds a user by username in Keycloak.
     *
     * @param username The username to search for
     * @return The user representation if found
     * @throws KeycloakUserNotFountException If the user is not found in Keycloak
     */
    UserRepresentation findByUsername(String username) throws KeycloakUserNotFountException;
}
