package com.example.msa.practice.gateway;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.List;
import java.util.function.Consumer;

@Slf4j(topic = "Gateway - Jwt Authentication filter")
@Component
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

    @Value("${jwt.secretKey}")
    private String secretKey;
    private final String AUTHORIZATION_HEADER = "Authorization";
    private final String AUTHORIZATION_KEY = "auth";
    private final String AUTHORIZATION_USERNAME = "username";
    private final String BEARER_PREFIX = "Bearer ";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        if (exchange.getRequest().getURI().getPath().startsWith("/auth")) {
            return chain.filter(exchange);
        }

        // 토큰 추출
        String token = extractToken(exchange);

        // 토큰 유효성 검사
        if (token == null || !validationToken(token)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // JWT 내의 정보 추출
        Claims claims = extractClaims(token);

        // 정보를 토대로 username, role 을 요청에 담기
        ServerWebExchange customExchange = setRequestInfo(exchange, claims);

        return chain.filter(customExchange);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }

    public String extractToken(ServerWebExchange exchange) {
        String bearerToken = exchange.getRequest().getHeaders().get(AUTHORIZATION_HEADER).getFirst();
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validationToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey));
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return false;
    }

    public Claims extractClaims(String token) {
        SecretKey key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey));
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public ServerWebExchange setRequestInfo(ServerWebExchange exchange, Claims claims) {
        return exchange.mutate().request(
                exchange.getRequest().mutate().headers(headers -> {
                    headers.set("X-ROLE", String.valueOf(claims.get(AUTHORIZATION_KEY)));
                    headers.set("X-USERNAME", String.valueOf(claims.get(AUTHORIZATION_USERNAME)));
                }).build()
        ).build();
    }
}
