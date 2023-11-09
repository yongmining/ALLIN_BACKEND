package com.allin.filmface.choiceContents.music.controller;

import com.allin.filmface.choiceContents.music.entity.GuestMusic;
import com.allin.filmface.choiceContents.music.service.GuestMusicService;
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
public class GuestMusicController {
    @Autowired
    private GuestEmotionRepository guestEmotionRepository;
    @Autowired
    private GuestMusicService guestMusicService;

    @GetMapping("/guestMusic/emotion")
    public ResponseEntity<ResponseDTO> getMusicVideosBasedOnGuestEmotion(@RequestParam(name = "guestNo") Integer guestNo) {
        GuestEmotion recentEmotion = guestEmotionRepository.findFirstByGuestNoOrderByGuestEmotionNoDesc(guestNo);
        List<GuestMusic> youtubeList;
        if (recentEmotion == null) {
            youtubeList = new ArrayList<>();
        } else {
            String guestEmotionResult = recentEmotion.getGuestEmotionResult();
            String query = "";
            switch (guestEmotionResult) {
                case "Sad":
                    query = "슬플떄 들으면 힘이나는 노래";
                    break;
                case "Angry":
                    query = "잔잔한 노래모음";
                    break;
                case "Neutral":
                case "Happy":
                    query = "신나는 노래모음집";
                    break;
                case "Surprise":
                case "Disgust":
                case "Fear":
                    query = "잔잔한 노래모음";
                    break;
                default:
                    youtubeList = new ArrayList<>();
                    return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK, "조회성공", youtubeList));
            }
            youtubeList = guestMusicService.getGuestMusicContentsByQuery(query, guestNo);
        }
        return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK, "조회성공", youtubeList));
    }
}
