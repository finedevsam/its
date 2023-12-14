package com.yorksj.itsproject.controller;

import com.yorksj.itsproject.dto.LoginDto;
import com.yorksj.itsproject.dto.RegisterDto;
import com.yorksj.itsproject.impl.ITSTreeService;
import com.yorksj.itsproject.impl.UserManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/")
public class AuthController {

    @Autowired
    private UserManagerImpl userManager;

    @Autowired
    private ITSTreeService itsTreeService;

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody RegisterDto registerDto){
        return userManager.register(registerDto);
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto){
        return userManager.login(loginDto);
    }

    @GetMapping("me")
    public ResponseEntity<?> loggedInUser(){
        return userManager.loggedInUser();
    }

    @GetMapping("tree/teacher")
    public ResponseEntity<?> teacherTree(){
        return itsTreeService.teacherTrees();
    }

}
