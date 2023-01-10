package com.example.demo.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccessTokenRepository extends JpaRepository<UserAccessToken, Integer> {
}
