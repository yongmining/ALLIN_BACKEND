package com.allin.filmface.choiceContents.music.controller;

import com.allin.filmface.choiceContents.music.entity.Music;
import com.allin.filmface.choiceContents.music.service.MusicService;
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
public class MusicController {

    @Autowired
    private MusicService musicService;
    @Autowired
    private EmotionRepository emotionRepository;

    @GetMapping("/music/emotion")
    public ResponseEntity<ResponseDTO> getMusicBasedOnEmotion(@RequestParam(name = "memberNo") Integer memberNo) {
        Emotion recentEmotion = emotionRepository.findFirstByMemberNoOrderByEmotionNoDesc(memberNo);
        List<Music> musicList;
        if (recentEmotion == null) {
            musicList = new ArrayList<>();
        } else {
            String emotionResult = recentEmotion.getEmotionResult();
            String query = "";
            switch (emotionResult) {
                case "Sad":
                    query = "슬픈 노래";
                    break;
                case "Angry":
                    query = "잔잔한 노래";
                    break;
                case "무표정":
                case "행복":
                    query = "신나는 노래";
                    break;
                case "놀람":
                case "역겨움":
                case "두려운":
                    query = "잔잔한 노래";
                    break;
                default:
                    musicList = new ArrayList<>();
                    return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK, "조회성공", musicList));
            }
            musicList = musicService.getMusicContentsByQuery(query, memberNo);
        }
            return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK, "조회성공", musicList));
        }
    }

