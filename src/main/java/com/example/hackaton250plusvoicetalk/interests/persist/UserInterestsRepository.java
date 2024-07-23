package com.example.hackaton250plusvoicetalk.interests.persist;

import com.example.hackaton250plusvoicetalk.interests.persist.entity.UserInterestsEntity;
import com.example.hackaton250plusvoicetalk.interests.persist.entity.UserInterestsPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserInterestsRepository extends JpaRepository<UserInterestsEntity, UserInterestsPK> {
}
