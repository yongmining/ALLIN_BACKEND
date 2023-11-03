package com.allin.filmface.choiceContents.music.dto;

import com.allin.filmface.member.dto.MemberSimpleDTO;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class MusicDTO {

    private int musicNo;

    private  String musicLink;

    private Integer niceCount;

    private String musicTitle;

    private MemberSimpleDTO member;

    private String thumbnailUrl;


}
