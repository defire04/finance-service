package com.example.financeservice.service.category.imp;

import com.example.financeservice.exception.category.CategoryDoesNotBelongToThisUserException;
import com.example.financeservice.exception.category.CategoryDoesNotExistException;
import com.example.financeservice.exception.category.CategoryWithThisNameAlreadyUserByCurrentUserException;
import com.example.financeservice.model.category.Category;
import com.example.financeservice.model.user.User;
import com.example.financeservice.repository.category.CategoryRepository;
import com.example.financeservice.service.base.imp.BaseEntityService;
import com.example.financeservice.service.category.ICategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CategoryService extends BaseEntityService<Category, CategoryRepository> implements ICategoryService {
    public CategoryService(CategoryRepository repository) {
        super(repository);
    }

    @Override
    @NonNull
    public Category create(@NonNull Category category) {

        repository.findByNameAndOwner(category.getName(), category.getOwner()).ifPresent(found -> {
            throw new CategoryWithThisNameAlreadyUserByCurrentUserException();
        });

        return super.create(category);
    }

    @Override
    @NonNull
    public Category update(@NonNull Category category) {

        checkCategoryOwnership(category);
        repository.findByNameAndOwner(category.getName(), category.getOwner()).ifPresent(found -> {
            throw new CategoryWithThisNameAlreadyUserByCurrentUserException();
        });

        return super.update(category);
    }

    @NonNull
    @Override
    public List<Category> getAll(String username) {
        return repository.findAllByOwnerUsername(username);
    }

    @Override
    public void delete(@NonNull Category category) {
        checkCategoryOwnership(category);
        super.delete(category);
    }

    @NonNull
    @Override
    public Boolean existsByIdAndOwnerUsername(Long categoryId, Long userId) {
        return repository.existsByIdAndOwnerId(categoryId, userId);
    }

    private void checkCategoryOwnership(@NonNull Category category) {
        Category existingCategory = repository.findById(category.getId())
                .orElseThrow(CategoryDoesNotExistException::new);
        User existingOwner = existingCategory.getOwner();
        User newOwner = category.getOwner();
        if (!Objects.equals(existingOwner.getId(), newOwner.getId())) {
            throw new CategoryDoesNotBelongToThisUserException();
        }
    }



}
