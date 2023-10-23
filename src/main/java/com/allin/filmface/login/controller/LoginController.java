package com.allin.filmface.login.controller;

import com.allin.filmface.common.ResponseDTO;
import com.allin.filmface.login.dto.AccessTokenDTO;
import com.allin.filmface.login.dto.KakaoAccessTokenDTO;
import com.allin.filmface.login.service.LoginService;
import com.allin.filmface.member.dto.MemberDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Api(tags = "로그인 내부 API")
@RestController
@RequestMapping("/api/v1/login")
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PreAuthorize("permitAll()")
    @ApiOperation(value = "카카오 인가 코드 받아와서 액세스 토큰 발급")
    @PostMapping("/kakaocode")
    public ResponseEntity<?> getKakaoCode(@RequestBody Map<String, String> code) {
        /* 인가 코드로 액세스 토큰 발급 */
        KakaoAccessTokenDTO kakaoToken = loginService.getAccessToken(code.get("code"));

        /* 액세스 토큰으로 DB 저장or 확인 후 JWT 생성 */
        loginService.getJwtToken(kakaoToken);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("token", kakaoToken);

        /* JWT와 응답 결과를 프론트에 전달*/
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "로그인 성공", responseMap));
    }

    @PostMapping("/kakaologout")
    public ResponseEntity<?> kakaoLogout(@RequestHeader("Authorization") String accessToken) {
        /* 카카오 로그아웃 API 호출 */
        boolean logoutSuccess = loginService.kakaoLogout(accessToken);

        if (logoutSuccess) {
            /* 로그아웃 성공 처리 */
            return ResponseEntity
                    .ok()
                    .body(new ResponseDTO(HttpStatus.OK, "로그아웃 성공", null));
        } else {
            /* 로그아웃 실패 처리 */
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseDTO(HttpStatus.BAD_REQUEST, "로그아웃 실패", null));
        }
    }


    @ApiOperation(value = "jwt 액세스 토큰 만료되어 재발급")
    @PostMapping("/renew")
    public ResponseEntity<?> renewAccessToken(@RequestHeader(value = "Auth") String auth) {
        try {
            // 인증 정보를 기반으로 JWT 액세스 토큰 재발급 로직 구현
            // ...

            // 재발급 성공 시 응답
            return ResponseEntity
                    .ok()
                    .body(new ResponseDTO(HttpStatus.OK, "액세스 토큰 재발급 성공", null));
        } catch (Exception e) {
            // 재발급 실패 시 에러 응답
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "액세스 토큰 재발급 실패", null));
        }
    }

    @ApiOperation(value = "비회원 로그인")
    @PostMapping("/guest")
    public ResponseEntity<?> guestLogin() {
        String guestToken = loginService.createGuestToken();
        MemberDTO guestMember = loginService.createGuestMember(guestToken);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("guestMember", guestMember);
        responseMap.put("token", guestToken);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "비회원 로그인 성공", responseMap));
    }
}

