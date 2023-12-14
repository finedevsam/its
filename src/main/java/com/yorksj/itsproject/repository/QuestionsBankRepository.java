package com.yorksj.itsproject.repository;

import com.yorksj.itsproject.entity.QuestionsBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionsBankRepository extends JpaRepository<QuestionsBank, String> {
    List<QuestionsBank> findAllByTopicsId(String topicId);
}
