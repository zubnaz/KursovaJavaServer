package org.example.Repositories;

import org.example.Entities.PostEntity;
import org.example.Entities.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity,Integer> {
}
