package com.example.testcontainers.repository;

import com.example.testcontainers.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = """
            UPDATE posts
            SET views_amount = views_amount + 1
            WHERE id = :id
            """, nativeQuery = true)
    @Modifying
    void addView(@Param("id") long id);

}
