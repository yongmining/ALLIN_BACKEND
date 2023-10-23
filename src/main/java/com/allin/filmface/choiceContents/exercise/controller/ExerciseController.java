package com.allin.filmface.choiceContents.exercise.controller;

import com.allin.filmface.choiceContents.exercise.entity.Exercise;
import com.allin.filmface.choiceContents.exercise.service.ExerciseService;
import com.allin.filmface.common.ResponseDTO;
import com.allin.filmface.emotion.entity.Emotion;
import com.allin.filmface.emotion.repository.EmotionRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class ExerciseController {

    @Autowired
    private ExerciseService exerciseService;
    @Autowired
    private EmotionRepository emotionRepository;

    @GetMapping("/exercise/emotion")
    public ResponseEntity<ResponseDTO> getExerciseBasedOnEmotion(@RequestParam(name = "memberNo") Integer memberNo) {
        Emotion recentEmotion = emotionRepository.findFirstByMemberNoOrderByEmotionNoDesc(memberNo);
        List<Exercise> exerciseList;
        if (recentEmotion == null) {
           exerciseList = new ArrayList<>();
        }else {
            String emotionResult = recentEmotion.getEmotionResult();
            String query = "";
            // 감정에 따른 운동 추천 쿼리를 설정합니다.
            // (실제 쿼리 내용은 프로젝트에 따라 변경해야 합니다.)
            switch (emotionResult) {
                case "슬픔":
                    query = "힐링 운동";
                    break;
                case "화남":
                    query = "에너지 소모 운동";
                    break;
                case "무표정":
                case "행복":
                    query = "기본 스트레칭";
                    break;
                default:
                    exerciseList = new ArrayList<>();
                    return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK, "조회성공", exerciseList));
            }
            exerciseList = exerciseService.getExerciseContentsByQuery(query, memberNo);
        }
        return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK, "조회성공", exerciseList));
    }
}
