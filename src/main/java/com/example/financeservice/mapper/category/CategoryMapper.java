package com.example.financeservice.mapper.category;

import com.example.financeservice.dto.category.CategoryDTO;
import com.example.financeservice.mapper.converter.ConverterUtil;
import com.example.financeservice.model.category.Category;
import com.example.financeservice.model.user.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    private final ModelMapper modelMapper;


    public CategoryMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;


        this.modelMapper.createTypeMap(Category.class, CategoryDTO.class)
                .addMapping(src -> src.getOwner().getId(), CategoryDTO::setOwnerId);


        this.modelMapper.createTypeMap(CategoryDTO.class, Category.class)
                .addMappings(map -> map.using(ConverterUtil.idToEntity(User::new))
                        .map(CategoryDTO::getOwnerId, Category::setOwner));
        ;
    }


    public Category toModel(CategoryDTO dto) {
        return modelMapper.map(dto, Category.class);
    }


    public CategoryDTO toDTO(Category model) {
        return modelMapper.map(model, CategoryDTO.class);
    }

}
