package com.allin.filmface.choiceContents.exercise.controller;

import com.allin.filmface.choiceContents.exercise.entity.GuestExercise;
import com.allin.filmface.choiceContents.exercise.service.GuestExerciseService;
import com.allin.filmface.common.ResponseDTO;
import com.allin.filmface.emotion.entity.GuestEmotion;
import com.allin.filmface.emotion.repository.GuestEmotionRepository;
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
public class GuestExerciseController {

    @Autowired
    private GuestEmotionRepository guestEmotionRepository;
    @Autowired
    private GuestExerciseService guestExerciseService;

    @GetMapping("/guestExercise/emotion")
    public ResponseEntity<ResponseDTO> getExerciseVideosBasedOnGuestEmotion(@RequestParam(name = "guestNo") Integer guestNo) {
        GuestEmotion recentEmotion = guestEmotionRepository.findFirstByGuestNoOrderByGuestEmotionNoDesc(guestNo);
        List<GuestExercise> youtubeList;
        if (recentEmotion == null) {
            youtubeList = new ArrayList<>();
        } else {
            String guestEmotionResult = recentEmotion.getGuestEmotionResult();
            String query = "";
            switch (guestEmotionResult) {
                case "Sad":
                    query = "힐링 운동";
                    break;
                case "Angry":
                    query = "에너지 소모 운동";
                    break;
                case "Neutral":
                case "Happy":
                    query = "기본 스트레칭";
                    break;
                case "Surprise":
                case "Disgust":
                case "Fear":
                    query = "조용한 운동";
                    break;
                default:
                    youtubeList = new ArrayList<>();
                    return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK, "조회성공", youtubeList));
            }

            youtubeList = guestExerciseService.getGuestExerciseContentsByQuery(query, guestNo);
        }
        return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK, "조회성공", youtubeList));
    }
}