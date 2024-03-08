package org.example.Repositories;

import org.example.Entities.CategoryEntity;
import org.example.Entities.PostTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostTagRepository extends JpaRepository<PostTagEntity,Integer> {
}
