package org.example.Repositories;

import org.example.Entities.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity,Integer> {
    Page<CategoryEntity> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<CategoryEntity> findByDescriptionContainingIgnoreCase(String description, Pageable pageable);
}
