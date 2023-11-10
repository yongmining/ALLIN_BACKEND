package com.allin.filmface.emotion.service;

import com.allin.filmface.emotion.dto.EmotionDTO;
import com.allin.filmface.emotion.entity.Emotion;
import com.allin.filmface.emotion.repository.EmotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmotionService {

    private final EmotionRepository emotionRepository;

    @Autowired
    public EmotionService(EmotionRepository emotionRepository) {
        this.emotionRepository = emotionRepository;
    }

    public Emotion saveEmotion(EmotionDTO emotionDTO) {
        // Create an Emotion entity from the DTO
        Emotion emotion = new Emotion();
        emotion.setEmotionResult(emotionDTO.getEmotionResult());
        emotion.setEmotionAge(emotionDTO.getEmotionAge());
        emotion.setEmotionGender(emotionDTO.getEmotionGender());

        // Save the entity to the database
        return emotionRepository.save(emotion);
    }

    public String getEmotionAge(Emotion emotion) {
        // Provide the logic to get emotion age
        // Assuming that Emotion entity has a getEmotionAge() method
        return emotion.getEmotionAge();
    }

    public String getEmotionGender(Emotion emotion) {
        // Provide the logic to get emotion gender
        // Assuming that Emotion entity has a getEmotionGender() method
        return emotion.getEmotionGender();
    }
}