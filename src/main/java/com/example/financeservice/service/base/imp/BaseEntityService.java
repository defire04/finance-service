package com.example.financeservice.service.base.imp;

import com.example.financeservice.model.BaseEntity;
import com.example.financeservice.repository.BaseEntityRepository;
import com.example.financeservice.service.base.IBaseEntityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

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
    public Page<E> getAll(PageRequest pageRequest) {
        return repository.findAll(pageRequest);
    }

    @Override
    public Optional<E> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public E create(E entity) {
        entity.setId(null);
        return repository.save(entity);
    }

    @Override
    @Transactional
    public E update(E entity) {
        return repository.save(entity);
    }

    @Override
    @Transactional
    public void delete(E entity) {
        repository.delete(entity);
    }


}
