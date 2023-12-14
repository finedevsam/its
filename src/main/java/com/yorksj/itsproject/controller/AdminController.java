package com.yorksj.itsproject.controller;

import com.yorksj.itsproject.dto.TeachesDto;
import com.yorksj.itsproject.impl.AdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/")
public class AdminController {

    @Autowired
    private AdminServiceImpl adminService;

    @PostMapping("assign/teacher")
    public ResponseEntity<?> assignTeaches(@RequestBody TeachesDto teachesDto){
        return adminService.Teaches(teachesDto);
    }

    @GetMapping("subject")
    public ResponseEntity<?> allSubject(Pageable pageable){
        return ResponseEntity.ok(adminService.AllSubjects(pageable));
    }

    @GetMapping("topics/{id}")
    public ResponseEntity<?> getTopics(Pageable pageable, @PathVariable String id){
        return ResponseEntity.ok(adminService.ViewTopics(id));
    }
}
