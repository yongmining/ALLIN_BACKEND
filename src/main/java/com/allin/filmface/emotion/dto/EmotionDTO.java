package com.allin.filmface.emotion.dto;

import com.allin.filmface.emotion.entity.Emotion;
import com.allin.filmface.emotion.service.EmotionService;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class EmotionDTO {

    private Long emotionNo;
    private String emotionResult;
    private String emotionAge;
    private String emotionGender;

    // Constructor to convert Emotion to EmotionDTO
    public EmotionDTO(Emotion emotion, EmotionService emotionService) {
        this.emotionNo = (long) emotion.getEmotionNo();
        this.emotionResult = emotion.getEmotionResult();
        this.emotionAge = emotionService.getEmotionAge(emotion);
        this.emotionGender = emotionService.getEmotionGender(emotion);
    }

    // Create an Emotion entity from EmotionDTO
    public Emotion getEmotion() {
        Emotion emotion = new Emotion();
        emotion.setEmotionNo(this.emotionNo.intValue());
        emotion.setEmotionResult(this.emotionResult);
        emotion.setEmotionAge(this.emotionAge);
        emotion.setEmotionGender(this.emotionGender);
        return emotion;
    }

    // Setter and Getter methods
}