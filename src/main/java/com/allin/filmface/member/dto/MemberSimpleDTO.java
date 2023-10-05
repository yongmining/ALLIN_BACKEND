package com.allin.filmface.member.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class MemberSimpleDTO {
    private int memberNo;
    private String memberNickname;
    private int memberAge;
    private String memberGender;
    private String memberImage;

}
