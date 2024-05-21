package com.example.financeservice.mapper.user;

import com.example.financeservice.dto.auth.SignInUserDTO;
import com.example.financeservice.model.user.User;
import org.keycloak.representations.idm.UserRepresentation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        setUserRepresentationToUser();
    }

    private void setUserRepresentationToUser() {
        this.modelMapper.createTypeMap(UserRepresentation.class, User.class)
                .addMappings(dto -> dto.skip(User::setId));
    }

    private void setSignIn() {
        this.modelMapper.createTypeMap(User.class, SignInUserDTO.class);
    }

    public User toModel(UserRepresentation dto) {
        return modelMapper.map(dto, User.class);
    }

    public UserRepresentation toUserRepresentation(User model) {
        return modelMapper.map(model, UserRepresentation.class);
    }

    public SignInUserDTO toSignInDTO(User model) {
        return modelMapper.map(model, SignInUserDTO.class);
    }
}
