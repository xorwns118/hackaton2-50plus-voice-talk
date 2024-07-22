package com.example.hackaton250plusvoicetalk.user.persist;

import com.example.hackaton250plusvoicetalk.user.persist.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByMobileNumber(String mobileNumber);

    Optional<UserEntity> findByUserId(Long userId);
}
