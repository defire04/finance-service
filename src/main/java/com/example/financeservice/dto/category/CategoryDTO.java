package com.example.financeservice.dto.category;

import com.example.financeservice.model.category.type.CategoryType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CategoryDTO {

    @JsonView(CategoryUpdateView.class)
    @Schema(description = "Category ID", example = "1")
    private Long id;

    @NotBlank
    @JsonView(CategoryCreateView.class)
    @Schema(description = "Category name", example = "Salary")
    private String name;


    @Schema(description = "Category type (INCOME or EXPENSE)", example = "INCOME")
    @NotBlank
    @JsonProperty("category_type")
    @JsonView(CategoryCreateView.class)
    private CategoryType categoryType;

    @JsonProperty("owner_id")
    @JsonIgnore
    @Schema(hidden = true)
    private Long ownerId;


    public interface CategoryCreateView {
    }

    public interface CategoryUpdateView extends CategoryCreateView {
    }

    public interface CategoryAll extends CategoryUpdateView {
    }
}
