package com.example.financeservice.mapper.converter;

import com.example.financeservice.model.BaseEntity;
import org.modelmapper.Converter;

import java.util.function.Supplier;

public class ConverterUtil {
    public static <ENTITY extends BaseEntity> Converter<Long, ENTITY> idToEntity(Supplier<ENTITY> entitySupplier) {
        return mappingContext -> {
            Long id = mappingContext.getSource();
            ENTITY entity = entitySupplier.get();
            if (id != null) {
                entity.setId(id);
                return entity;
            }
            return null;
        };
    }
}
