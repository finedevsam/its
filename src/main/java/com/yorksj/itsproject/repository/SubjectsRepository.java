package com.yorksj.itsproject.repository;

import com.yorksj.itsproject.entity.Subjects;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectsRepository extends JpaRepository<Subjects, String> {
    boolean existsById(String Id);

    Subjects findSubjectsById(String Id);
}
