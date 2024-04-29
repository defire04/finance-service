package com.example.financeservice.controller.category;

import com.example.financeservice.dto.category.CategoryDTO;
import com.example.financeservice.dto.response.ResponseDTO;
import com.example.financeservice.dto.response.ResponseListDTO;
import com.example.financeservice.exception.user.UserPrincipalNotFoundException;
import com.example.financeservice.mapper.category.CategoryMapper;
import com.example.financeservice.model.category.Category;
import com.example.financeservice.service.category.imp.CategoryService;
import com.example.financeservice.service.user.imp.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    private final CategoryMapper categoryMapper;

    private final UserService userService;

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
    public ResponseListDTO<List<CategoryDTO>> getAllByUser(@RequestParam(required = false, defaultValue = "0") int page,
                                                           @RequestParam(required = false, defaultValue = "5") int size,
                                                           Principal principal) {
        Page<Category> pageData = categoryService.getAll(principal.getName(), page, size);
        List<CategoryDTO> categoryDTOs = pageData.getContent().stream()
                .map(categoryMapper::toDTO)
                .toList();

        return ResponseListDTO.<List<CategoryDTO>>builder()
                .currentPage(pageData.getNumber())
                .size(pageData.getSize())
                .data(categoryDTOs)
                .totalElements(pageData.getTotalElements())
                .totalPages(pageData.getTotalPages())
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