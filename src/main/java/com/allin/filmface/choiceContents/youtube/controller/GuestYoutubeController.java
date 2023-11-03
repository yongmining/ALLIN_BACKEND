package com.allin.filmface.choiceContents.youtube.controller;

import com.allin.filmface.choiceContents.youtube.entity.GuestYoutube;
import com.allin.filmface.choiceContents.youtube.service.GuestYoutubeService;
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

public class GuestYoutubeController {

    @Autowired
    private GuestEmotionRepository guestEmotionRepository;
    @Autowired
    private GuestYoutubeService guestYoutubeService;


    @GetMapping("/guestYoutube/emotion")
    public ResponseEntity<ResponseDTO> getYoutubeVideosBasedOnGuestEmotion(@RequestParam(name = "guestNo") Integer guestNo) {
        // 해당 memberNo의 마지막 emotionResult를 가져옵니다.
        GuestEmotion recentEmotion = guestEmotionRepository.findFirstByGuestNoOrderByGuestEmotionNoDesc(guestNo);
        List<GuestYoutube> youtubeList;
        if (recentEmotion == null) {
            youtubeList = new ArrayList<>();
        } else {
            String guestEmotionResult = recentEmotion.getGuestEmotionResult();
            String query = "";
            switch(guestEmotionResult) {
                case "Sad":
                    query = "슬픔을 극복하기";
                    break;
                case "Angry":
                    query = "화날 때 볼만한 영상";
                    break;
                case "Neutral":
                case "Happy":
                    query = "재밌는 영상";
                    break;
                case "Surprise":
                case "Disgust":
                case "Fear":
                    query = "차분한 영상";
                    break;
                default:
                    youtubeList = new ArrayList<>();
                    return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK, "조회성공", youtubeList));
            }
            youtubeList = guestYoutubeService.getGuestYoutubeContentsByQuery(query, guestNo);
        }
        return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK, "조회성공", youtubeList));
    }
}
