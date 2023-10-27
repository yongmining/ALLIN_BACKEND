package com.allin.filmface.login.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class UnloginDTO {
    private int guestNo;
    private String guest_token;
    private String code;
    private String token_type;
    private String loginType;
}
