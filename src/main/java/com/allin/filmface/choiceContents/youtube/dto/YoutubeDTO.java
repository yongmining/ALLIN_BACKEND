package com.allin.filmface.choiceContents.youtube.dto;


import com.allin.filmface.member.dto.MemberSimpleDTO;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class YoutubeDTO {

    private int youtubeNo;

    private  String youtubeLink;

    private Integer niceCount;

    private String youtubeTitle;

    private MemberSimpleDTO member;

    private String thumbnailUrl;

    private Integer emotionNo;

}
