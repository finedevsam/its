package com.yorksj.itsproject.impl;

import com.yorksj.itsproject.config.UserInfoUserDetailsService;
import com.yorksj.itsproject.dto.FeedBackDto;
import com.yorksj.itsproject.dto.LoginDto;
import com.yorksj.itsproject.dto.RegisterDto;
import com.yorksj.itsproject.entity.*;
import com.yorksj.itsproject.repository.*;
import com.yorksj.itsproject.service.JwtService;
import com.yorksj.itsproject.service.UserManager;
import com.yorksj.itsproject.utils.AuthenticatedUser;
import com.yorksj.itsproject.utils.DataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserManagerImpl implements UserManager {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DataResponse dataResponse;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserInfoUserDetailsService userDetailsService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthenticatedUser authenticatedUser;

    @Autowired
    private StudiesRepository studiesRepository;

    @Autowired
    private SubjectsRepository subjectsRepository;

    @Autowired
    private TopicsRepository topicsRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionsBankRepository questionsBankRepository;

    @Autowired
    private FeedbacksRepository feedbacksRepository;


    @Override
    public ResponseEntity<?> register(RegisterDto registerDto) {
        if(userRepository.existsByEmail(registerDto.getEmail())){
            return dataResponse.dataResponse("01", "fail", dataResponse.message("Email Taken"), HttpStatus.BAD_REQUEST);
        }

        String userId = null;
        User user = new User();
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setIsStudent(true);
        userId = String.format("S-%s", userId());
        Profile profile = new Profile();
        profile.setFirstName(registerDto.getFirstName());
        profile.setLastName(registerDto.getLastName());
        profile.setUser(user);
        profile.setIdNo(userId);

        userRepository.save(user);
        profileRepository.save(profile);
        return dataResponse.dataResponse("00", "success", dataResponse.message("Registration Successfully"),  HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> login(LoginDto loginDto) {
        try {
            final UserDetails userDetails = userDetailsService.loadUserByUsername(loginDto.getEmail());
            User user = userRepository.findUserByEmail(userDetails.getUsername());
            Map<Object, Object> data = new HashMap<>();
            Map<Object, Object> auth = new HashMap<>();

            final String token = jwtService.generateToken(userDetails.getUsername());
            try {
                authenticate(user.getEmail(), loginDto.getPassword());

                Profile profile = profileRepository.findUserProfileByUserId(user.getId());
                auth.put("accessToken", token);

                String role = null;
                if(user.getIsStudent()){
                    role = "student";
                }else {
                    role = "teacher";
                }
                data.put("auth", auth);
                data.put("email", user.getEmail());
                data.put("firstName", profile.getFirstName());
                data.put("lastName", profile.getLastName());
                data.put("IdNumber", profile.getIdNo());
                data.put("role", role);
                return dataResponse.dataResponse("00", "success", data, HttpStatus.OK);

            } catch (Exception e) {
                return dataResponse.dataResponse("99", "fail", dataResponse.message(e.getMessage()), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return dataResponse.dataResponse("99", "fail", dataResponse.message(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> loggedInUser() {
        User user = authenticatedUser.auth();
        Profile profile = profileRepository.findUserProfileByUserId(user.getId());
        Map<Object, Object> data = new HashMap<>();
        String role = null;
        if(user.getIsStudent()){
            role = "student";
        }else {
            role = "teacher";
        }
        data.put("email", user.getEmail());
        data.put("firstName", profile.getFirstName());
        data.put("lastName", profile.getLastName());
        data.put("IdNumber", profile.getIdNo());
        data.put("role", role);
        return dataResponse.dataResponse("00", "success", data, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> enrol(String subjectId) {
        User user = authenticatedUser.auth();

        if (!subjectsRepository.existsById(subjectId)){
            return dataResponse.dataResponse("99", "fail", "Invalid subject ID", HttpStatus.BAD_REQUEST);
        }
        if (studiesRepository.existsByUserIdAndSubjectsId(user.getId(), subjectId)){
            return dataResponse.dataResponse("99", "fail", "Student already enrol to this topic", HttpStatus.BAD_REQUEST);
        }

        Subjects subjects = subjectsRepository.findSubjectsById(subjectId);
        Studies studies = new Studies();
        studies.setUser(user);
        studies.setSubjects(subjects);

        Quiz quiz = new Quiz();
        quiz.setSubjects(subjects);
        quiz.setUser(user);

        quizRepository.save(quiz);
        studiesRepository.save(studies);

        return dataResponse.dataResponse("00", "success", "You have successfully enrol to this subject", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> studentEnrolledSubject() {
        User user = authenticatedUser.auth();
        Studies allSubjectStudies = studiesRepository.findAllByUserId(user.getId());

        return dataResponse.dataResponse("00", "success", allSubjectStudies, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> viewTopics(String subjectId) {
        User user = authenticatedUser.auth();
        List<Topics> topics = topicsRepository.findAllBySubjectsId(subjectId);
        return dataResponse.dataResponse("00", "success", topics, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> startSubject(String studiesId) {
        User user = authenticatedUser.auth();
        Studies studies = studiesRepository.findByUserIdAndId(user.getId(), studiesId);
        if(Objects.isNull(studies)){
            return dataResponse.dataResponse("99", "fail", "Invalid ID", HttpStatus.BAD_REQUEST);
        }
        if(studies.isStartSubject()){
            return dataResponse.dataResponse("99", "fail", "Subject has already been started", HttpStatus.BAD_REQUEST);
        }
        studies.setStartSubject(true);
        studiesRepository.save(studies);
        return dataResponse.dataResponse("00", "success", "Subject Started", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> viewStartProject(String studiesId) {
        User user = authenticatedUser.auth();
        Studies studies = studiesRepository.findByUserIdAndId(user.getId(), studiesId);
        if(Objects.isNull(studies)){

            return dataResponse.dataResponse("99", "fail", "Invalid ID", HttpStatus.BAD_REQUEST);
        }

        if(!studies.isStartSubject()){
            return dataResponse.dataResponse("99", "fail", "Course has not started", HttpStatus.BAD_REQUEST);
        }

        List<Topics> topics = topicsRepository.findAllBySubjectsId(studies.getSubjects().getId());
        Map<String, Object> data = new HashMap<>();
        data.put("courseWork", topics);
        if (studies.isCompleted()){

            List<List<QuestionsBank>> qs = new ArrayList<>();
            for(Topics i: topics){
                List<QuestionsBank> questionsBank = questionsBankRepository.findAllByTopicsId(i.getId());
                qs.add(questionsBank);
            }
            data.put("quiz", qs);

        }
        return dataResponse.dataResponse("00", "success", data, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> startQuiz(String studiesId) {
        User user = authenticatedUser.auth();
        Studies studies = studiesRepository.findByUserIdAndId(user.getId(), studiesId);
        if(Objects.isNull(studies)){
            return dataResponse.dataResponse("99", "fail", "Invalid ID", HttpStatus.BAD_REQUEST);
        }
        studies.setCompleted(true);
        studiesRepository.save(studies);
        return dataResponse.dataResponse("00", "success", "Quiz Started", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> feedback(String topicId, FeedBackDto feedBackDto) {
        User user = authenticatedUser.auth();
        Topics topics = topicsRepository.findTopicsById(topicId);
        if(Objects.isNull(topics)){
            return dataResponse.dataResponse("99", "fail", "Invalid ID", HttpStatus.BAD_REQUEST);
        }

        if(feedbacksRepository.existsByTopicsId(topicId)){
            return dataResponse.dataResponse("99", "fail", "Feedback has been given for this topic", HttpStatus.BAD_REQUEST);
        }

        Feedbacks feedbacks = new Feedbacks();
        feedbacks.setUser(user);
        feedbacks.setTopics(topics);
        feedbacks.setComment(feedBackDto.getComment());
        feedbacksRepository.save(feedbacks);
        return dataResponse.dataResponse("00", "success", "Thank you for your feedback", HttpStatus.OK);
    }

    //    Generate ID for Students and Teacher
    private static String userId() {
        Random random = new Random();
        int maxValue = (int) Math.pow(10, 7);
        int randomNumber = random.nextInt(maxValue);
        return String.valueOf(randomNumber);
    }

    private void authenticate(String email, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new Exception("User disabled");
        } catch (BadCredentialsException e) {
            throw new Exception("Bad Credentials");
        }
    }
}
