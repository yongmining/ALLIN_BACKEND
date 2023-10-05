package com.allin.filmface.login.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class AccessTokenDTO {

    private String grantType;
    private long memberNo;
    private String accessToken;
    private long accessTokenExpiresIn;
    private String loginType;
}