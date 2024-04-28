package com.example.financeservice.config;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public Keycloak keycloak(@Value("${keycloak.realm}") String realm,
                             @Value("${keycloak.admin}") String adminUsername,
                             @Value("${keycloak.password}") String password,
                             @Value("${keycloak.url}") String url,
                             @Value("${keycloak.client}") String clientId,
                             @Value("${keycloak.secret}") String secret) {

        return KeycloakBuilder.builder()
                .serverUrl(url)
                .realm(realm)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .clientId(clientId)
                .clientSecret(secret)
                .grantType("password")
                .username(adminUsername)
                .password(password)
                .build();
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
//        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setPropertyCondition(Conditions.isNotNull())
                .setSkipNullEnabled(true);

        return modelMapper;
    }
}
