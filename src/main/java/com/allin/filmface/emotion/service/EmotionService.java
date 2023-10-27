package com.allin.filmface.emotion.service;


import com.allin.filmface.emotion.dto.EmotionDTO;
import com.allin.filmface.emotion.entity.Emotion;
import com.allin.filmface.emotion.repository.EmotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmotionService {

    public List<EmotionDTO> analyzeEmotion(EmotionDTO EmotionDTO) {

        List<EmotionDTO> emotions = new ArrayList<>();

        // 여기에 감정분석 로직을 구현합니다.

        return emotions;
    }

    @Autowired
    private EmotionRepository emotionRepository;

    public List<EmotionDTO> getEmotionsByMemberNo(Integer memberNo) {
        List<Emotion> emotions = emotionRepository.findByMemberNo(memberNo);
        return emotions.stream()
                .map(e -> new EmotionDTO(
                        e.getEmotionNo(),
                        e.getEmotionResult(),
                        null,
                        null))
                .collect(Collectors.toList());
    }
}