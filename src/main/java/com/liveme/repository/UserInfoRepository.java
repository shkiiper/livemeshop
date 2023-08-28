package com.liveme.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.liveme.entity.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
    Optional<UserInfo> findByName(String username);

    Optional<UserInfo> findByEmail(String email);
}
