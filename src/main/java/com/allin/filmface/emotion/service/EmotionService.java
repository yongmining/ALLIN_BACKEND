package com.allin.filmface.emotion.service;


import com.allin.filmface.emotion.dto.EmotionDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmotionService {

    public List<EmotionDTO> analyzeEmotion(EmotionDTO EmotionDTO) {

        List<EmotionDTO> emotions = new ArrayList<>();

        // 여기에 감정분석 로직을 구현합니다.

        return emotions;
    }
}