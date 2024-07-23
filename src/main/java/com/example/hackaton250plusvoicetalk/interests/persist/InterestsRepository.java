package com.example.hackaton250plusvoicetalk.interests.persist;

import com.example.hackaton250plusvoicetalk.interests.persist.entity.InterestsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterestsRepository extends JpaRepository<InterestsEntity, Long> {
}
