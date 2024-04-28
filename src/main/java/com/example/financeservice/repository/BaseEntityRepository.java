package com.example.financeservice.repository;

import com.example.financeservice.model.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseEntityRepository<E extends BaseEntity> extends JpaRepository<E, Long> {
}
