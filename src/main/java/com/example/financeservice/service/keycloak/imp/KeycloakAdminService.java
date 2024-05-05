package com.example.financeservice.service.keycloak.imp;

import com.example.financeservice.dto.auth.RegisterDTO;
import com.example.financeservice.exception.registration.RegistrationException;
import com.example.financeservice.exception.user.UserNotFountException;
import com.example.financeservice.service.keycloak.IAdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class KeycloakAdminService implements IAdminService {

    @Value("${keycloak.realm}")
    private String realm;

    private final Keycloak keycloak;

    @Override
    public RegisterDTO createUser(RegisterDTO userRegistrationRecord) throws RegistrationException {
        UserRepresentation user = getUserRepresentation(userRegistrationRecord);

        UsersResource usersResource = getUsersResource();


        try (Response response = usersResource.create(user)) {

            if (response.getStatus() == Response.Status.CREATED.getStatusCode()) {
                return userRegistrationRecord;
            } else {
                Map<String, String> resultMap = response.readEntity(new GenericType<>() {
                });
                throw new RegistrationException(response.getStatus(), resultMap.get("errorMessage"));
            }
        } catch (Exception e) {
            String errorMessage = e.getMessage();

            if (e instanceof RegistrationException registrationException) {
                throw new RegistrationException(registrationException.getStatusCode(), errorMessage);
            } else {
                throw new RegistrationException(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), errorMessage);
            }


        }
    }

    @Override
    public UserRepresentation findByUsername(String username) {
        return getUsersResource().searchByUsername(username, true).stream().findFirst().orElseThrow(() ->
                new UserNotFountException(username));
    }

    private static UserRepresentation getUserRepresentation(RegisterDTO userRegistrationRecord) {
        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(userRegistrationRecord.getUsername());
        user.setEmail(userRegistrationRecord.getEmail());
        user.setFirstName(userRegistrationRecord.getFirstName());
        user.setLastName(userRegistrationRecord.getLastName());
        user.setEmailVerified(false);

        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setValue(userRegistrationRecord.getPassword());
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);

        List<CredentialRepresentation> list = new ArrayList<>();
        list.add(credentialRepresentation);
        user.setCredentials(list);
        return user;
    }


    private UsersResource getUsersResource() {
        RealmResource resource = keycloak.realm(realm);
        return resource.users();
    }


}
