package com.sparta.msa_exam.auth;

import com.sparta.msa_exam.auth.user.UserRoleEnum;
import com.sparta.msa_exam.auth.user.User;
import com.sparta.msa_exam.auth.user.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j(topic = "auth-service: jwtUtil")
@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.issuer}")
    private String issuer;
    @Value("${jwt.expiration}")
    private Long expiration;
    @Value("${jwt.secretKey}")
    private String secretKey;
    @Value("${admin.code}")
    private String ADMIN_CODE;
    private Key key;
    private final String AUTHORIZATION_HEADER = "Authorization";
    private final String AUTHORIZATION_KEY = "auth";
    private final String BEARER_PREFIX = "Bearer ";
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    public String createToken(String username, UserRoleEnum role) {
        return BEARER_PREFIX + Jwts.builder()
                .claim("username", username)
                .claim(AUTHORIZATION_KEY, role.getAuthority())
                .setIssuer(issuer)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key, signatureAlgorithm)
                .compact();
    }

    public void signup(String username, String password) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user != null) {
            throw new IllegalArgumentException("Duplicated username!");
        }
        String encodedPassword = passwordEncoder.encode(password);
//        if (code != null && code.equals(ADMIN_CODE)) {
//            log.info("Find Admin Code! Your Authority is Admin!");
//            userRepository.save(new User(username, encodedPassword, UserRoleEnum.ADMIN));
//        } else {
//            log.info("You are common User");
//
//        }
        userRepository.save(new User(username, encodedPassword, UserRoleEnum.USER));
    }

    public String signin(String username, String password) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("No Search Username!"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Not Anything Matched Password!");
        }
        return createToken(user.getUsername(), user.getRole());
    }
}
