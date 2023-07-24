package com.liveme.repository;

import com.liveme.entity.Thumbnail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThumbnailRepository extends JpaRepository<Thumbnail, Integer> {
}
