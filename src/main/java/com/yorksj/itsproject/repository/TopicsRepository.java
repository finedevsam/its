package com.yorksj.itsproject.repository;

import com.yorksj.itsproject.entity.Topics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicsRepository extends JpaRepository<Topics, String> {

    List<Topics> findAllBySubjectsId(String subject_id);
    Topics findTopicsById(String Id);
}
