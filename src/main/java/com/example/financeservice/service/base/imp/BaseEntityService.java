package com.example.financeservice.service.base.imp;

import com.example.financeservice.model.BaseEntity;
import com.example.financeservice.repository.BaseEntityRepository;
import com.example.financeservice.service.base.IBaseEntityService;

import java.util.List;
import java.util.Optional;

public class BaseEntityService<E extends BaseEntity, R extends BaseEntityRepository<E>> implements IBaseEntityService<E> {

    protected final R repository;

    public BaseEntityService(R repository) {
        this.repository = repository;
    }

    @Override
    public List<E> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<E> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public E create(E entity) {
        entity.setId(null);
        return repository.save(entity);
    }

    @Override
    public E update(E entity) {
        return repository.save(entity);
    }

    @Override
    public void delete(E entity) {
        repository.delete(entity);
    }


}
