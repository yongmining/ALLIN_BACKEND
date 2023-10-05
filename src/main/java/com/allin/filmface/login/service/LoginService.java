package com.allin.filmface.login.service;

import com.allin.filmface.login.dto.AccessTokenDTO;
import com.allin.filmface.login.dto.KakaoAccessTokenDTO;
import com.allin.filmface.login.dto.RenewTokenDTO;
import com.allin.filmface.member.dto.MemberDTO;
import com.allin.filmface.member.entity.Member;
import com.allin.filmface.member.service.MemberService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

@Service
public class LoginService {
    private final MemberService memberService;

    @Value("${oauth.Kakao.client-id}")
    private String KAKAO_CLIENT_ID;


    public LoginService(MemberService memberService) {
        this.memberService = memberService;

    }

    public KakaoAccessTokenDTO getAccessToken(String code) {

        RestTemplate rt = new RestTemplate();
        rt.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", KAKAO_CLIENT_ID);
        params.add("redirect_uri", "http://localhost:3000/kakao/callback");
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        ResponseEntity<String> accessTokenResponse = rt.exchange("https://kauth.kakao.com/oauth/token", HttpMethod.POST,
                kakaoTokenRequest, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        KakaoAccessTokenDTO kakaoToken = null;
        try {
            kakaoToken = objectMapper.readValue(accessTokenResponse.getBody(), KakaoAccessTokenDTO.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        kakaoToken.setLoginType("kakao");

        return kakaoToken;
    }

    public String findMemberImage(String imageUrl) {
        Random random = new Random();
        int randomSeed = random.nextInt(10000);
        return "https://api.dicebear.com/6.x/thumbs/png?seed=" + randomSeed;
    }

    @Transactional
    public void getJwtToken(KakaoAccessTokenDTO kakaoToken) {
        // 해당 유저의 가입 이력을 확인
        if (memberService.findBySocialId("KAKAO", String.valueOf(kakaoToken.getMemberNo())) == null) {
            MemberDTO newMember = new MemberDTO();

            newMember.setSocialLogin("KAKAO");
            newMember.setSocialId(String.valueOf(kakaoToken.getMemberNo()));

            newMember.setRefreshToken(kakaoToken.getRefresh_token());
            newMember.setRefreshTokenExpireDate(kakaoToken.getRefresh_token_expires_in() + System.currentTimeMillis());

            newMember.setAccessToken(kakaoToken.getAccess_token());
            newMember.setAccessTokenExpireDate(kakaoToken.getExpires_in() + System.currentTimeMillis());

            newMember.setMemberImage(findMemberImage(newMember.getSocialId()));

            memberService.registNewUser(newMember);
        }

        /* 소셜 아이디로 멤버가 있는지 조회해 가져옴 */
        Member foundmember = memberService.findBySocialId("KAKAO", String.valueOf(kakaoToken.getMemberNo()));
        kakaoToken.setMemberNo(foundmember.getMemberNo());

        /* 액세스토큰, 리프레시 토큰 업데이트 */
        foundmember.setRefreshToken(kakaoToken.getRefresh_token());
        foundmember.setAccessToken(kakaoToken.getAccess_token());
        foundmember.setRefreshTokenExpireDate(kakaoToken.getRefresh_token_expires_in() + System.currentTimeMillis());
        foundmember.setAccessTokenExpireDate(kakaoToken.getExpires_in() + System.currentTimeMillis());


        Date accessExpireDate = new Date(foundmember.getAccessTokenExpireDate());

        if (accessExpireDate.before(new Date(System.currentTimeMillis()))) {

            RenewTokenDTO renewedToken = renewKakaoToken(foundmember);

            if (renewedToken.getRefresh_token() != null) {

                foundmember.setRefreshToken(renewedToken.getRefresh_token());
                foundmember.setRefreshTokenExpireDate(
                        renewedToken.getRefresh_token_expires_in() + System.currentTimeMillis());
            }

            foundmember.setAccessToken(renewedToken.getAccess_token());
            foundmember.setAccessTokenExpireDate(renewedToken.getExpires_in() + System.currentTimeMillis());

        }
    }


    public RenewTokenDTO renewKakaoToken(Member foundMember) {

        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "refresh_token");
        params.add("client_id", KAKAO_CLIENT_ID);
        params.add("refresh_token", foundMember.getRefreshToken());


        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        ResponseEntity<String> renewTokenResponse = rt.exchange("https://kauth.kakao.com/oauth/token", HttpMethod.POST,
                kakaoTokenRequest, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        RenewTokenDTO renewToken = null;
        try {
            renewToken = objectMapper.readValue(renewTokenResponse.getBody(), RenewTokenDTO.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return renewToken;
    }

    public boolean kakaoLogout(String accessToken) {

        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.add("Authorization", "Bearer " + accessToken);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        HttpEntity<MultiValueMap<String, String>> kakaoLogoutRequest = new HttpEntity<>(params, headers);

        ResponseEntity<String> kakaoLogoutResponse = rt.exchange("https://kapi.kakao.com/v1/user/logout",
                //                "https://kapi.kakao.com/v1/user/unlink",
                HttpMethod.POST, kakaoLogoutRequest, String.class);

        return kakaoLogoutResponse.getStatusCode().is2xxSuccessful();
    }

    public String createGuestToken() {
        String guestId = "GUEST_" + System.currentTimeMillis();

        return "게스트 로그인이 되었습니다.";
    }
}
