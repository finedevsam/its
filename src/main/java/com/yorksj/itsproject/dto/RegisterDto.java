package com.yorksj.itsproject.dto;

import lombok.Data;

@Data
public class RegisterDto {
    /* This is the class that convert the user inputted data to Java object */
    private String firstName;

    private String lastName;

    private String email;

    private String password;
}
