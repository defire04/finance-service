package com.example.financeservice.mapper.balance.transaction;

import com.example.financeservice.dto.account.balance.transaction.BalanceTransactionDTO;
import com.example.financeservice.mapper.converter.ConverterUtil;
import com.example.financeservice.model.account.balance.Balance;
import com.example.financeservice.model.account.balance.transaction.BalanceTransaction;
import com.example.financeservice.model.category.Category;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BalanceTransactionMapper {

    private final ModelMapper modelMapper;


    public BalanceTransactionMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;


        this.modelMapper.createTypeMap(BalanceTransaction.class, BalanceTransactionDTO.class)
                .addMapping(src -> src.getBalance().getId(), BalanceTransactionDTO::setBalanceId)
                .addMapping(src -> src.getCategory().getId(), BalanceTransactionDTO::setCategoryId)

        ;


        this.modelMapper.createTypeMap(BalanceTransactionDTO.class, BalanceTransaction.class)
                .addMappings(map -> map.using(ConverterUtil.idToEntity(Balance::new))
                        .map(BalanceTransactionDTO::getBalanceId, BalanceTransaction::setBalance))
                .addMappings(map -> map.using(ConverterUtil.idToEntity(Category::new))
                        .map(BalanceTransactionDTO::getCategoryId, BalanceTransaction::setCategory))
        ;
    }

    public BalanceTransaction toModel(BalanceTransactionDTO dto) {
        return modelMapper.map(dto, BalanceTransaction.class);
    }

    public BalanceTransactionDTO toDTO(BalanceTransaction model) {
        return modelMapper.map(model, BalanceTransactionDTO.class);
    }
}
