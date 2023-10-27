package com.allin.filmface.jwt;

import com.allin.filmface.member.dto.MemberDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expirationMs}")
    private long jwtExpirationMs;

    public String generateToken(String code) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + jwtExpirationMs);

        Claims claims = Jwts.claims()
                .setIssuedAt(now)
                .setExpiration(expiration)
                .setSubject("guest");

        claims.put("code", code);

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public boolean validateGuestToken(String token, String code) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token)
                    .getBody();

            if (!claims.getSubject().equals("guest")) {
                return false;
            }

            String tokenCode = claims.get("code", String.class);
            return code.equals(tokenCode);
        } catch (SignatureException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}


