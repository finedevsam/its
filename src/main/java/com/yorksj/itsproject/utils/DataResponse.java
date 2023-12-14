package com.yorksj.itsproject.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DataResponse {

    public ResponseEntity<?> dataResponse(String statusCode, String status, Object response, HttpStatus httpStatus){
        Map<String, Object> data = new HashMap<>();
        data.put("statusCode", statusCode);
        data.put("status", status);
        data.put("statusResponse", response);
        return new ResponseEntity<>(data, httpStatus);
    }

    public Map<Object, String> message(String message){
        Map<Object, String> data = new HashMap<>();
        data.put("message", message);
        return data;
    }

}
