package com.yorksj.itsproject.repository;

import com.yorksj.itsproject.entity.Studies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudiesRepository extends JpaRepository<Studies, String> {

    boolean existsByUserIdAndSubjectsId(String userId, String subjectId);

    Studies findAllByUserId(String userId);

    Studies findByUserIdAndId(String userId, String Id);
}
