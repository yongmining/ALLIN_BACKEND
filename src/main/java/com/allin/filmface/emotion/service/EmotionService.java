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

    public List<EmotionDTO> analyzeEmotion(EmotionDTO emotionDTO) {
        List<EmotionDTO> emotions = new ArrayList<>();

        // 여기에 감정 분석 로직을 구현합니다.
        // 이 부분에서 Flask 서버로부터 반환된 JSON 결과를 가져와서 필요한 정보를 EmotionDTO에 저장하면 됩니다.

        // 예시: JSON 결과를 가져오는 코드 (실제 서비스에 맞게 수정 필요)
        // String jsonResult = callFlaskServerAndGetJSON();

        // JSON 결과에서 감정, 나이, 성별 정보 추출
        // emotionDTO.setEmotionAge("23"); // 예시: 나이 정보 설정
        // emotionDTO.setEmotionResult("Happy"); // 예시: 감정 정보 설정
        // emotionDTO.setEmotionGender("Male"); // 예시: 성별 정보 설정

        // 결과를 리스트에 추가
        emotions.add(emotionDTO);

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
