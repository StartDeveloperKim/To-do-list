package project.todo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import project.todo.domain.entity.User;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
@PropertySource("classpath:application-jwt.properties")
public class TokenProvider {
    private final String SECRET_KEY;

    @Autowired
    public TokenProvider(@Value("${jwt.secret}") String SECRET_KEY) {
        this.SECRET_KEY = SECRET_KEY;
    }

    public String create(User user) {
        // 기한을 지금으로부터 1일로 설정
        Date expiryDate = Date.from(
                Instant.now().plus(1, ChronoUnit.DAYS));

        return Jwts.builder()
                // Header
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                // Payload
                .setSubject(user.getId())
                .setIssuer("demo app")
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .compact();
    }

    public String validateAndGetUserId(String token) {

        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY) // 시크릿키 설정
                .parseClaimsJws(token)// Base64로 디코딩 및 파싱
                .getBody(); // userId가 필요

        return claims.getSubject();
    }
}
