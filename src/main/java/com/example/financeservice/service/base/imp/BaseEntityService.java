package com.example.financeservice.service.base.imp;

import com.example.financeservice.model.BaseEntity;
import com.example.financeservice.repository.BaseEntityRepository;
import com.example.financeservice.service.base.IBaseEntityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public class BaseEntityService<E extends BaseEntity, R extends BaseEntityRepository<E>> implements IBaseEntityService<E> {

    protected final R repository;

    public BaseEntityService(R repository) {
        this.repository = repository;
    }

    @Override
    @NonNull
    public List<E> getAll() {
        return repository.findAll();
    }

    @Override
    @NonNull
    public Page<E> getAll(@NonNull PageRequest pageRequest) {
        return repository.findAll(pageRequest);
    }

    @Override
    @NonNull
    public Optional<E> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @NonNull
    @Transactional
    public E create(@NonNull E entity) {
        entity.setId(null);
        return repository.save(entity);
    }

    @NonNull
    @Override
    @Transactional
    public E update(@NonNull E entity) {
        return repository.save(entity);
    }

    @Override
    @Transactional
    public void delete(@NonNull E entity) {
        repository.delete(entity);
    }


}
