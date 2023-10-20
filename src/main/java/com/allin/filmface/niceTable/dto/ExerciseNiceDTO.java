package com.allin.filmface.niceTable.dto;

import com.allin.filmface.member.dto.MemberSimpleDTO;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class ExerciseNiceDTO {

    private MemberSimpleDTO member;

    private int niceNo;
    private int ExerciseNo;
}
