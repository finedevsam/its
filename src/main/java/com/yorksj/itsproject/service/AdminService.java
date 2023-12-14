package com.yorksj.itsproject.service;

import com.yorksj.itsproject.dto.TeachesDto;
import com.yorksj.itsproject.entity.Subjects;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdminService {
    ResponseEntity<?> Teaches(TeachesDto teachesDto);

    List<Subjects> AllSubjects(Pageable pageable);

    ResponseEntity<?> ViewTopics(String subject_id);
}
