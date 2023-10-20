package com.allin.filmface.choiceContents.exercise.controller;
import com.allin.filmface.choiceContents.exercise.entity.Exercise;
import com.allin.filmface.choiceContents.exercise.service.ExerciseService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/exercise/emotion")
    public List<Exercise> getExerciseByEmotion(@RequestParam String emotionResult) {
        String query = "";
        switch(emotionResult) {
            case "슬픔":
                query = "조용한 요가"; // 운동 검색조건: 슬픔
                break;
            case "화남":
                query = "높은 강도의 운동"; // 운동 검색조건: 화남
                break;
            case "무표정":
            case "행복":
                query = "즐거운 춤 운동"; // 운동 검색조건: 무표정, 행복
                break;
            case "놀람":
            case "역겨움":
            case "두려운":
                query = "딥 브리딩 운동"; // 운동 검색조건: 놀람, 역겨움, 두려운
                break;
            default:
                return new ArrayList<>();
        }
        return exerciseService.getExerciseContentsByQuery(query);
    }
}