package com.example.financeservice.service.category;

import com.example.financeservice.exception.category.CategoryDoesNotExistException;
import com.example.financeservice.exception.category.CategoryWithThisNameAlreadyUserByCurrentUserException;
import com.example.financeservice.model.category.Category;
import com.example.financeservice.model.category.type.CategoryType;
import com.example.financeservice.model.user.User;
import com.example.financeservice.repository.category.CategoryRepository;
import com.example.financeservice.service.category.imp.CategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;


    @Test
    @DisplayName("Successful Category Creation")
    public void testCreateCategory_Success() {
        Category category = new Category()
                .setName("Test Category")
                .setOwner(new User())
                .setCategoryType(CategoryType.EXPENSE);

        when(categoryRepository.findByNameAndOwner(category.getName(), category.getOwner())).thenReturn(Optional.empty());
        when(categoryRepository.save(any())).thenReturn(category);

        Category createdCategory = categoryService.create(category);

        assertNotNull(createdCategory);
        assertEquals("Test Category", createdCategory.getName());
    }

    @Test
    @DisplayName("Category Creation with Duplicate Name")
    public void testCreateCategory_DuplicateName() {
        Category existingCategory = new Category()
                .setName("Test Category")
                .setOwner(new User())
                .setCategoryType(CategoryType.EXPENSE);

        when(categoryRepository.findByNameAndOwner(existingCategory.getName(), existingCategory.getOwner())).thenReturn(Optional.of(existingCategory));

        assertThrows(CategoryWithThisNameAlreadyUserByCurrentUserException.class, () -> {
            categoryService.create(existingCategory);
        });
    }


    @Test
    @DisplayName("Update Category - Successful")
    public void testUpdateCategory_Successful() {
        Category categoryToUpdate = new Category()
                .setName("Existing Name")
                .setOwner(new User());
        categoryToUpdate.setId(1L);

        when(categoryRepository.findByNameAndOwner(anyString(), any(User.class))).thenReturn(Optional.empty());
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(categoryToUpdate));
        when(categoryRepository.save(any(Category.class))).thenReturn(categoryToUpdate);

        Category updatedCategory = categoryService.update(categoryToUpdate);

        verify(categoryRepository).save(categoryToUpdate);

        assertEquals(categoryToUpdate, updatedCategory);
    }


    @Test
    @DisplayName("Update Category - Category Does Not Exist")
    public void testUpdateCategory_CategoryDoesNotExist() {
        Category categoryToUpdate = new Category()

                .setName("Existing Name")
                .setOwner(new User());
        categoryToUpdate.setId(1L);

        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(CategoryDoesNotExistException.class, () -> {
            categoryService.update(categoryToUpdate);
        });

        verify(categoryRepository, never()).save(any(Category.class));
    }


    @Test
    @DisplayName("Delete Category - Successful")
    public void testDeleteCategory_Success() {
        User existingOwner = new User();
        User newOwner = new User();
        existingOwner.setId(1L);
        newOwner.setId(1L);


        Category categoryToDelete = new Category()

                .setName("Existing Name")
                .setOwner(existingOwner);
        categoryToDelete.setId(1L);

        when(categoryRepository.findById(categoryToDelete.getId())).thenReturn(Optional.of(categoryToDelete));

        categoryService.delete(categoryToDelete);

        verify(categoryRepository).delete(categoryToDelete);
    }

    @Test
    @DisplayName("Delete Category - Category Does Not Exist")
    public void testDeleteCategory_CategoryDoesNotExist() {
        Category categoryToDelete = new Category();
        categoryToDelete.setId(1L);
        when(categoryRepository.findById(categoryToDelete.getId())).thenReturn(Optional.empty());

        assertThrows(CategoryDoesNotExistException.class, () -> {
            categoryService.delete(categoryToDelete);
        });
    }

    @DisplayName("Check if Category exists by ID and Owner ID")
    @Test
    public void testExistsByIdAndOwnerUsername() {
        Long categoryId = 1L;
        Long userId = 1L;
        boolean expectedResult = true;

        when(categoryRepository.existsByIdAndOwnerId(categoryId, userId)).thenReturn(expectedResult);

        Boolean result = categoryService.existsByIdAndOwnerUsername(categoryId, userId);

        verify(categoryRepository).existsByIdAndOwnerId(categoryId, userId);

        assertTrue(result);
    }


}
