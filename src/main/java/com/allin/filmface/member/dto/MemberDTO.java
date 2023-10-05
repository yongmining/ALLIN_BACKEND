package com.allin.filmface.member.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class MemberDTO {

    private int memberNo;
    private String memberNickname;
    private int memberAge;
    private String memberGender;
    private String memberImage;

    private String socialLogin;
    private String socialId;
    private String accessToken;
    private long accessTokenExpireDate;
    private String refreshToken;
    private long refreshTokenExpireDate;
}