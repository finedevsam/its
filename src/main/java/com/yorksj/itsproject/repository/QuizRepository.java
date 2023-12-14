package com.yorksj.itsproject.repository;

import com.yorksj.itsproject.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, String> {

    List<Quiz> findAllBySubjectsId(String subjectId);
}
