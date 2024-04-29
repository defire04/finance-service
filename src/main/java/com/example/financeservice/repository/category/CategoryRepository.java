package com.example.financeservice.repository.category;

import com.example.financeservice.model.category.Category;
import com.example.financeservice.model.user.User;
import com.example.financeservice.repository.BaseEntityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends BaseEntityRepository<Category> {

    public Optional<Category> findByNameAndOwner(String name, User owner);

    public Page<Category> findAllByOwnerUsername(String ownerUsername, PageRequest pageRequest);


}
