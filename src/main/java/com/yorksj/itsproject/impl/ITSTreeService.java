package com.yorksj.itsproject.impl;

import com.yorksj.itsproject.entity.Profile;
import com.yorksj.itsproject.entity.Subjects;
import com.yorksj.itsproject.entity.Teaches;
import com.yorksj.itsproject.entity.User;
import com.yorksj.itsproject.repository.ProfileRepository;
import com.yorksj.itsproject.repository.SubjectsRepository;
import com.yorksj.itsproject.repository.TeachesRepository;
import com.yorksj.itsproject.service.ITSTree;
import com.yorksj.itsproject.utils.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ITSTreeService implements ITSTree {

    @Autowired
    private AuthenticatedUser authenticatedUser;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private SubjectsRepository subjectsRepository;

    @Autowired
    private TeachesRepository teachesRepository;

    @Override
    public ResponseEntity<?> teacherTrees() {
        User user = authenticatedUser.auth();
        Profile profile = profileRepository.findUserProfileByUserId(user.getId());
        Teaches teaches = teachesRepository.findByUserId(user.getId());
        Subjects subjects = subjectsRepository.findSubjectsById(teaches.getSubjects().getId());
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> profileData = new HashMap<>();
        Map<String, Object> subjectData = new HashMap<>();

        String role = null;
        if(user.getIsStudent()){
            role = "STUDENT";
            profileData.put("studentId", profile.getIdNo());
        }else if(user.getIsTeacher()){
            role = "TEACHER";
            profileData.put("staffId", profile.getIdNo());
        } else {
            role = "ADMIN";
            profileData.put("studentId", "ADMIN");
        }
        profileData.put("name", String.format("%s %s", profile.getFirstName().toUpperCase(), profile.getLastName().toUpperCase()));
        profileData.put("role", role);
        data.put("profile", profileData);
        data.put("teaches", subjects);
        return ResponseEntity.ok(data);
    }
}
