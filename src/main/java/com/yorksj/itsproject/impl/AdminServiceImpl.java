package com.yorksj.itsproject.impl;

import com.yorksj.itsproject.dto.TeachesDto;
import com.yorksj.itsproject.entity.*;
import com.yorksj.itsproject.repository.*;
import com.yorksj.itsproject.service.AdminService;
import com.yorksj.itsproject.utils.DataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private DataResponse dataResponse;

    @Autowired
    private TeachesRepository teachesRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubjectsRepository subjectsRepository;

    @Autowired
    private TopicsRepository topicsRepository;

    @Override
    public ResponseEntity<?> Teaches(TeachesDto teachesDto) {
//        if(!Objects.equals(role, "student") && ! Objects.equals(role, "teacher")){
//            return dataResponse.dataResponse("01", "fail", dataResponse.message("Account type can either be 'student' or 'teacher'"), HttpStatus.BAD_REQUEST);
//        }
        if(!userRepository.existsById(teachesDto.getTeacherId())){
            return dataResponse.dataResponse("01", "fail", dataResponse.message("Invalid Teacher ID"), HttpStatus.BAD_REQUEST);
        }

        if(!subjectsRepository.existsById(teachesDto.getSubjectId())){
            return dataResponse.dataResponse("01", "fail", dataResponse.message("Invalid Subject ID"), HttpStatus.BAD_REQUEST);
        }
        if(teachesRepository.existsTeachesBySubjectsIdAndUserId(teachesDto.getSubjectId(), teachesDto.getTeacherId())){
            return dataResponse.dataResponse("01", "fail", dataResponse.message(getUserName(teachesDto.getTeacherId())), HttpStatus.BAD_REQUEST);
        }
        User user = userRepository.findUserById(teachesDto.getTeacherId());
        Subjects subjects = subjectsRepository.findSubjectsById(teachesDto.getSubjectId());
        Teaches teaches = new Teaches();
        teaches.setUser(user);
        teaches.setSubjects(subjects);
        teachesRepository.save(teaches);
        return dataResponse.dataResponse("00", "success", dataResponse.message(String.format("%s Assigned to %s", getUserName(teachesDto.getTeacherId()), getSubjectName(teachesDto.getSubjectId()))), HttpStatus.OK);
    }

    @Override
    public List<Subjects> AllSubjects(Pageable pageable) {
        return subjectsRepository.findAll(pageable).toList();
    }

    @Override
    public ResponseEntity<?> ViewTopics(String subject_id) {
        Subjects subjects = subjectsRepository.findSubjectsById(subject_id);
        List<Topics> topics = topicsRepository.findAllBySubjectsId(subjects.getId());
        Map<Object, Object> data = new HashMap<>();
        data.put("subjectName", subjects.getName());
        data.put("topics", topics);
        return ResponseEntity.ok(data);
    }


    private String getUserName(String id){
        Profile profile = profileRepository.findUserProfileByUserId(id);
        return String.format("%s %s", profile.getFirstName().toUpperCase(), profile.getLastName().toUpperCase());
    }

    private String getSubjectName(String id){
        Subjects subjects = subjectsRepository.findSubjectsById(id);
        return subjects.getName();
    }
}
