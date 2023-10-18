package com.allin.filmface.search.dto;

import com.allin.filmface.emotion.dto.EmotionDTO;
import com.allin.filmface.member.dto.MemberSimpleDTO;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class SearchDTO {

    private MemberSimpleDTO member;
    private EmotionDTO emotion;

    private int searchNo;
    private int contentNo;



}
