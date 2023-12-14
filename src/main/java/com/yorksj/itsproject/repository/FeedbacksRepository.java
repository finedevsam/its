package com.yorksj.itsproject.repository;

import com.yorksj.itsproject.entity.Feedbacks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbacksRepository extends JpaRepository<Feedbacks, String> {
    boolean existsByTopicsId(String topicId);
}
