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

    public void setEmotionResult(String emotionResult) {
        this.emotionResult = emotionResult;
    }

    public void setEmotionAge(String emotionAge) {
        this.emotionAge = emotionAge;
    }

    public void setEmotionGender(String emotionGender) {
        this.emotionGender = emotionGender;
    }
}