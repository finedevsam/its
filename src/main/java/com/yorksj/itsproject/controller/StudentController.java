package com.yorksj.itsproject.controller;


import com.yorksj.itsproject.dto.FeedBackDto;
import com.yorksj.itsproject.impl.UserManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student/")
public class StudentController {

    @Autowired
    private UserManagerImpl userManager;


    @GetMapping("enrol/{subjectId}")
    public ResponseEntity<?> enrolToSubject(@PathVariable String subjectId){
        return userManager.enrol(subjectId);
    }

    @GetMapping("enrolled/subject")
    public ResponseEntity<?> enrolledSubject(){
        return userManager.studentEnrolledSubject();
    }

    @GetMapping("enrolled/subject/{subjectId}/details")
    public ResponseEntity<?> viewEnrolledSubjectDetail(@PathVariable String subjectId){
        return userManager.viewTopics(subjectId);
    }

    @GetMapping("enrolled/subject/{studiesId}/start")
    public ResponseEntity<?> startSubject(@PathVariable String studiesId){
        return userManager.startSubject(studiesId);
    }

    @GetMapping("enrolled/subject/{studiesId}/ongoing")
    public ResponseEntity<?> viewOngoingSubject(@PathVariable String studiesId){
        return userManager.viewStartProject(studiesId);
    }

    @GetMapping("enrolled/subject/{studiesId}/start-quiz")
    public ResponseEntity<?> StartQuiz(@PathVariable String studiesId){
        return userManager.startQuiz(studiesId);
    }

    @PostMapping("enrolled/subject/{topicId}/feedback")
    public ResponseEntity<?> giveFeedbacks(@PathVariable String topicId, @RequestBody FeedBackDto feedBackDto){
        return userManager.feedback(topicId, feedBackDto);
    }
}
