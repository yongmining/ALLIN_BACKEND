package com.allin.filmface.feedback.dto;

import com.allin.filmface.member.dto.MemberSimpleDTO;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class FeedbackDTO {
    private long feedbackNo;
    private String feedbackContent;
    private LocalDate feedbackDate;
    private int feedbackStar;
    private MemberSimpleDTO member;

}
