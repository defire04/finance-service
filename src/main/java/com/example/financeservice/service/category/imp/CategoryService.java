package com.example.financeservice.service.category.imp;

import com.example.financeservice.exception.category.CategoryDoesNotBelongToThisUserException;
import com.example.financeservice.exception.category.CategoryDoesNotExistException;
import com.example.financeservice.exception.category.CategoryWithThisNameAlreadyUserByCurrentUserException;
import com.example.financeservice.model.category.Category;
import com.example.financeservice.model.user.User;
import com.example.financeservice.repository.category.CategoryRepository;
import com.example.financeservice.service.base.imp.BaseEntityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CategoryService extends BaseEntityService<Category, CategoryRepository> {
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
    public Page<Category> getAll(String username, int size, int page) {
        return repository.findAllByOwnerUsername(username, PageRequest.of(size, page));
    }

    @Override
    public void delete(@NonNull Category category) {
        checkCategoryOwnership(category);
        super.delete(category);
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