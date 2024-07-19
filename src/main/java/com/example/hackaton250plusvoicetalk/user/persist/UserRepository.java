package com.example.hackaton250plusvoicetalk.user.persist;

import com.example.hackaton250plusvoicetalk.user.persist.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
}
