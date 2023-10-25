package com.allin.filmface.member.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class GuestDTO {
    private int guestNo;
    private String guestNickname;
    private int guestAge;
    private String guestGender;
    private String guestImage;

    private String socialLogin;
    private String socialCode;
    private String accessToken;
}
