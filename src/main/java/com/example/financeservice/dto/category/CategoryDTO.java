package com.example.financeservice.dto.category;

import com.example.financeservice.model.category.type.CategoryType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CategoryDTO {

    @JsonView(CategoryUpdateView.class)
    private Long id;

    @NotBlank
    @JsonView(CategoryCreateView.class)
    private String name;


    @NotBlank
    @JsonProperty("category_type")
    @JsonView(CategoryCreateView.class)
    private CategoryType categoryType;

    @JsonProperty("owner_id")
    @JsonIgnore
    private Long ownerId;


    public interface CategoryCreateView {
    }

    public interface CategoryUpdateView extends CategoryCreateView {
    }

    public interface CategoryAll extends CategoryUpdateView {
    }
}
