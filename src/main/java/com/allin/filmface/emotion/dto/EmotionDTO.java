package com.allin.filmface.emotion.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class EmotionDTO {
    private int emotionNo;
    private String emotionResult;
    private String emotionAge;
    private String emotionGender;
}