package com.example.financeservice.mapper.balance;

import com.example.financeservice.dto.account.balance.BalanceDTO;
import com.example.financeservice.dto.account.balance.RegisterBalanceDTO;
import com.example.financeservice.mapper.converter.ConverterUtil;
import com.example.financeservice.model.account.balance.Balance;
import com.example.financeservice.model.user.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BalanceMapper {

    private final ModelMapper modelMapper;

    public BalanceMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;


        this.modelMapper.createTypeMap(Balance.class, BalanceDTO.class);
//                .addMapping(src -> src.getOwner().getId(), BalanceDTO::setOwnerId);

        this.modelMapper.createTypeMap(BalanceDTO.class, Balance.class);
//                .addMappings(map -> map.using(ConverterUtil.idToEntity(User::new))
//                        .map(BalanceDTO::getOwnerId, Balance::setOwner));


    }


    public Balance toModel(BalanceDTO dto) {
        return modelMapper.map(dto, Balance.class);
    }
    public Balance toModel(RegisterBalanceDTO dto) {
        return modelMapper.map(dto, Balance.class);
    }

    public BalanceDTO toDTO(Balance model) {
        return modelMapper.map(model, BalanceDTO.class);
    }


}

