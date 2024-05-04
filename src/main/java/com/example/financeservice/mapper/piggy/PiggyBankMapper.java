package com.example.financeservice.mapper.piggy;

import com.example.financeservice.dto.account.piggy.PiggyBankDTO;
import com.example.financeservice.mapper.converter.ConverterUtil;
import com.example.financeservice.model.account.piggy.PiggyBank;
import com.example.financeservice.model.user.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PiggyBankMapper {

    private final ModelMapper modelMapper;

    public PiggyBankMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;


        this.modelMapper.createTypeMap(PiggyBank.class, PiggyBankDTO.class)
                .addMapping(src -> src.getOwner().getId(), PiggyBankDTO::setOwnerId);

        this.modelMapper.createTypeMap(PiggyBankDTO.class, PiggyBank.class)
                .addMappings(map -> map.using(ConverterUtil.idToEntity(User::new))
                        .map(PiggyBankDTO::getOwnerId, PiggyBank::setOwner));

    }


    public PiggyBank toModel(PiggyBankDTO dto) {
        return modelMapper.map(dto, PiggyBank.class);
    }

    public PiggyBankDTO toDTO(PiggyBank model) {
        return modelMapper.map(model, PiggyBankDTO.class);
    }
}
