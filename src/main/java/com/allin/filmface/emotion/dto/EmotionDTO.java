package com.allin.filmface.emotion.dto;

import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class EmotionDTO {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int emotionNo;

    private String emotionResult;
    private String emotionAge;
    private String emotionGender;
}