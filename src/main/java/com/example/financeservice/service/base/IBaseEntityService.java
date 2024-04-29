package com.example.financeservice.service.base;

import com.example.financeservice.model.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

public interface IBaseEntityService<E extends BaseEntity> {

    List<E> getAll();

    Optional<E> findById(Long id);

    E create(E entity);

    E update(E entity);

    void delete(E entity);

    Page<E> getAll(PageRequest pageRequest);
}
