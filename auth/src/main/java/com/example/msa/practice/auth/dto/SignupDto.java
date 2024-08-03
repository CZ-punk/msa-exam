package com.example.msa.practice.auth.dto;

import lombok.Data;

@Data
public class SignupDto {

    private String username;
    private String password;
    private String code;
}
