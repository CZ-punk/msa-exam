package com.example.msa.practice.auth;

import com.example.msa.practice.auth.user.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupDto requestDto) {
        authService.signup(requestDto.username, requestDto.paasword, requestDto.adminCode);
        return ResponseEntity.ok("Success SignUp!");
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody SigninDto requestDto) {
        String token = authService.signin(requestDto.username, requestDto.password);
        return ResponseEntity.ok(token);
    }


    public class SignupDto {
        private String username;
        private String paasword;
        private String adminCode;
    }

    public class SigninDto {
        private String username;
        private String password;
    }
}
