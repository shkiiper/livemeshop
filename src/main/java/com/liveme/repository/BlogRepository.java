package com.liveme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.liveme.entity.Blog;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
}
