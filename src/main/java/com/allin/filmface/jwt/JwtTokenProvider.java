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

    public String generateToken() {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + jwtExpirationMs);

        Claims claims = Jwts.claims()
                .setIssuedAt(now)
                .setExpiration(expiration)
                .setSubject("guest"); // 사용자 식별 정보 (guest 또는 다른 값을 사용할 수 있음)


        // 이미지를 Base64로 인코딩하여 토큰에 추가
//        claims.put("image", imageUrl);

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }
    public MemberDTO getMemberFromToken(String token) {
        try {
            // 토큰을 파싱하여 Claims를 얻습니다.
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token)
                    .getBody();

            // Claims에서 회원 정보를 추출합니다.
            String memberNickname = claims.get("memberNickname", String.class);
            int memberAge = claims.get("memberAge", Integer.class);
            String memberGender = claims.get("memberGender", String.class);
            String memberImage = claims.get("memberImage", String.class);

            // 추출한 정보를 MemberDTO 객체로 만듭니다.
            MemberDTO memberDTO = new MemberDTO();
            memberDTO.setMemberNickname(memberNickname);
            memberDTO.setMemberAge(memberAge);
            memberDTO.setMemberGender(memberGender);
            memberDTO.setMemberImage(memberImage);

            return memberDTO;
        } catch (Exception e) {
            return null;
        }
    }
}

