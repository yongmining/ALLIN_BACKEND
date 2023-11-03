package com.allin.filmface.choiceContents.exercise.dto;

import com.allin.filmface.member.dto.MemberSimpleDTO;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class ExerciseDTO {
    private int exerciseNo;

    private String exerciseLink;

    private Integer niceCount;

    private  String exerciseTitle;

    private MemberSimpleDTO member;

    private String thumbnailUrl;





}
