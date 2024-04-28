package com.example.financeservice.service.base;

import com.example.financeservice.model.BaseEntity;

import java.util.List;
import java.util.Optional;

public interface IBaseEntityService<E extends BaseEntity> {

    public List<E> getAll();

    public Optional<E> findById(Long id);

    public E create(E entity);

    public E update(E entity);

    public void delete(E entity);
}
