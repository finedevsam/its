package com.yorksj.itsproject.repository;

import com.yorksj.itsproject.entity.Teaches;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeachesRepository extends JpaRepository<Teaches, String> {
    boolean existsTeachesBySubjectsIdAndUserId(String subjectId, String userId);

    Teaches findByUserId(String Id);
}
