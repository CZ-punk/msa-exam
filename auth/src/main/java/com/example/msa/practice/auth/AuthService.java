package com.example.msa.practice.auth;

import com.example.msa.practice.auth.user.User;
import com.example.msa.practice.auth.user.UserRepository;
import com.example.msa.practice.auth.user.UserRoleEnum;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    public String createToken (String username, UserRoleEnum role) {
        return BEARER_PREFIX + Jwts.builder()
                .claim("username", username)
                .claim(AUTHORIZATION_KEY, role.getAuthority())
                .setIssuer(issuer)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key, signatureAlgorithm)
                .compact();
    }

    public String getJwtFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validationToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return false;
    }

    public void signup(String username, String password, String code) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            throw new IllegalArgumentException("Duplicated username!");
        }
        String encodedPassword = passwordEncoder.encode(password);
        if (code.equals(ADMIN_CODE)) {
            log.info("Find Admin Code! Your Authority is Admin!");
            userRepository.save(new User(username, encodedPassword, UserRoleEnum.ADMIN));
        } else {
            log.info("You are common User");
            userRepository.save(new User(username, encodedPassword, UserRoleEnum.USER));
        }
    }

    public String signin(String username, String password) {
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new IllegalArgumentException("No Search Username!"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Not Anything Matched Password!");
        }

        return createToken(user.getUsername(), user.getRole());
    }
}
