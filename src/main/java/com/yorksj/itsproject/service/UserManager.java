package com.yorksj.itsproject.service;

import com.yorksj.itsproject.dto.FeedBackDto;
import com.yorksj.itsproject.dto.LoginDto;
import com.yorksj.itsproject.dto.RegisterDto;
import com.yorksj.itsproject.entity.Subjects;
import org.springframework.http.ResponseEntity;

public interface UserManager {

    ResponseEntity<?> register(RegisterDto registerDto);

    ResponseEntity<?> login(LoginDto loginDto);

    ResponseEntity<?> loggedInUser();

    ResponseEntity<?> enrol(String subjectId);

    ResponseEntity<?> studentEnrolledSubject();

    ResponseEntity<?> viewTopics(String subjectId);

    ResponseEntity<?> startSubject(String studiesId);

    ResponseEntity<?> viewStartProject(String studiesId);

    ResponseEntity<?> startQuiz(String studiesId);

    ResponseEntity<?> feedback(String topicId, FeedBackDto feedBackDto);

}
