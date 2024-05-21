package com.example.financeservice.service.category;

import com.example.financeservice.exception.category.CategoryDoesNotBelongToThisUserException;
import com.example.financeservice.exception.category.CategoryWithThisNameAlreadyUserByCurrentUserException;
import com.example.financeservice.model.category.Category;
import com.example.financeservice.service.base.IBaseEntityService;
import org.springframework.data.domain.Page;
import org.springframework.lang.NonNull;

import java.util.List;

public interface ICategoryService extends IBaseEntityService<Category> {


    /**
     * Creates a new category.
     *
     * @param category The category to create
     * @return The created category
     * @throws CategoryWithThisNameAlreadyUserByCurrentUserException If a category with this name is already used by the current user
     */
    @NonNull
    Category create(@NonNull Category category);

    /**
     * Updates an existing category.
     *
     * @param category The category to update
     * @return The updated category
     * @throws CategoryWithThisNameAlreadyUserByCurrentUserException If a category with this name is already used by the current user
     */
    @NonNull
    Category update(@NonNull Category category);

    /**
     * Retrieves all categories belonging to a specific user.
     *
     * @param username The username of the user
     * @return A page of categories belonging to the specified user
     */
    @NonNull
    List<Category> getAll(String username);

    /**
     * Deletes a category.
     *
     * @param category The category to delete
     * @throws CategoryDoesNotBelongToThisUserException If the category does not belong to the current user
     */
    void delete(@NonNull Category category);

    /**
     * Checks if a category with a given ID belongs to a specific user.
     *
     * @param categoryId The ID of the category
     * @param userId     The ID of the user
     * @return True if the category exists and belongs to the specified user, false otherwise
     */
    @NonNull
    Boolean existsByIdAndOwnerUsername(Long categoryId, Long userId);
}
