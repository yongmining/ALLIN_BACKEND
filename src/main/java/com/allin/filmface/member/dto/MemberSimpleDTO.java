package com.allin.filmface.member.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class MemberSimpleDTO {
    private long memberNo;
    private String memberNickname;
    private long memberAge;
    private String memberGender;
    private String memberImage;

}
