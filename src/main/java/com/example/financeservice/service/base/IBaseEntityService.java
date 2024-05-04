package com.example.financeservice.service.base;

import com.example.financeservice.model.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface IBaseEntityService<E extends BaseEntity> {

    /**
     * Retrieves all entities.
     *
     * @return A list of all entities
     */
    @NonNull
    List<E> getAll();

    /**
     * Finds an entity by its ID.
     *
     * @param id The ID of the entity to find
     * @return An optional containing the entity if found, or empty otherwise
     */
    @NonNull
    Optional<E> findById( Long id);

    /**
     * Creates a new entity.
     *
     * @param entity The entity to create
     * @return The created entity
     */
    @NonNull
    E create(@NonNull E entity);

    /**
     * Updates an existing entity.
     *
     * @param entity The entity to update
     * @return The updated entity
     */
    @NonNull
    E update(@NonNull E entity);

    /**
     * Deletes an entity.
     *
     * @param entity The entity to delete
     */
    void delete(@NonNull E entity);

    /**
     * Retrieves all entities with pagination.
     *
     * @param pageRequest The pagination information
     * @return A page of entities
     */
    @NonNull
    Page<E> getAll(@NonNull PageRequest pageRequest);
}
