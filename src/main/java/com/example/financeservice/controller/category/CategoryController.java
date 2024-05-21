package com.example.financeservice.controller.category;

import com.example.financeservice.dto.category.CategoryDTO;
import com.example.financeservice.dto.response.ResponseDTO;
import com.example.financeservice.exception.user.UserPrincipalNotFoundException;
import com.example.financeservice.mapper.category.CategoryMapper;
import com.example.financeservice.model.category.type.CategoryType;
import com.example.financeservice.service.category.ICategoryService;
import com.example.financeservice.service.user.IUserService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final ICategoryService categoryService;
    private final IUserService userService;
    private final CategoryMapper categoryMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO<CategoryDTO> create(@RequestBody @JsonView({CategoryDTO.CategoryCreateView.class}) CategoryDTO categoryDTO,
                                           Principal principal) {
        return ResponseDTO.<CategoryDTO>builder()
                .data(userService.findByUsername(principal.getName())
                        .map(user -> categoryService.create(categoryMapper.toModel(categoryDTO.setOwnerId(user.getId()))))
                        .map(categoryMapper::toDTO)
                        .orElseThrow(UserPrincipalNotFoundException::new))
                .build();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<List<CategoryDTO>> getAllByUser(@RequestParam("category_type") CategoryType categoryType, Principal principal) {


        return ResponseDTO.<List<CategoryDTO>>builder()
                .data(categoryService.getAll(principal.getName()).stream().filter(category -> category.getCategoryType().equals(categoryType)).map(categoryMapper::toDTO).toList())
                .build();
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<CategoryDTO> update(@RequestBody @JsonView({CategoryDTO.CategoryUpdateView.class}) CategoryDTO categoryDTO,
                                           Principal principal) {
        return ResponseDTO.<CategoryDTO>builder()
                .data(userService.findByUsername(principal.getName())
                        .map(user -> categoryService.update(categoryMapper.toModel(categoryDTO.setOwnerId(user.getId()))))
                        .map(categoryMapper::toDTO)
                        .orElseThrow(UserPrincipalNotFoundException::new))
                .build();
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestBody @JsonView({CategoryDTO.CategoryUpdateView.class}) CategoryDTO categoryDTO,
                       Principal principal) {

        userService.findByUsername(principal.getName()).ifPresent(user -> {
            categoryService.delete(categoryMapper.toModel(categoryDTO.setOwnerId(user.getId())));
        });
    }
}
