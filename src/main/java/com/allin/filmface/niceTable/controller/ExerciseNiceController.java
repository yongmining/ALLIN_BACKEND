package com.allin.filmface.niceTable.controller;

import com.allin.filmface.choiceContents.exercise.dto.ExerciseDTO;
import com.allin.filmface.niceTable.dto.ExerciseNiceDTO;
import com.allin.filmface.niceTable.service.exerciseNiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ExerciseNiceController {

    private final exerciseNiceService exerciseNiceService;

    @Autowired
    public ExerciseNiceController(exerciseNiceService exerciseNiceService) {
        this.exerciseNiceService = exerciseNiceService;
    }

    @PostMapping("/createOrCancelExerciseNice")
    public ResponseEntity<String> createOrCancelNice(@RequestBody ExerciseNiceDTO exerciseNiceDTO) {
        exerciseNiceService.createOrCancelExerciseNice(exerciseNiceDTO);
        System.out.println("Received member_No: " + exerciseNiceDTO.getMember().getMemberNo());
        return new ResponseEntity<>("Nice created or canceled successfully", HttpStatus.OK);
    }

    @GetMapping("/nicecount/{exerciseLink}")
    public ResponseEntity<Long> getNiceCount(@PathVariable String exerciseLink) {
        Long count = exerciseNiceService.getNiceCountForExercise(exerciseLink);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @GetMapping("/exerciserecommendations/emotionage/{memberNo}")
    public ResponseEntity<List<ExerciseDTO>> getRecommendedExerciseByEmotionAndAge(@PathVariable int memberNo) {
        List<ExerciseDTO> recommendedExercise = exerciseNiceService.getRecommendedExercisesByEmotionAndAge(memberNo);
        System.out.println("Recommended exercise count: " + recommendedExercise.size());
        return new ResponseEntity<>(recommendedExercise, HttpStatus.OK);
    }
}
