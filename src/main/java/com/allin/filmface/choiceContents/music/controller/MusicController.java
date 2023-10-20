package com.allin.filmface.choiceContents.music.controller;

import com.allin.filmface.choiceContents.music.entity.Music;
import com.allin.filmface.choiceContents.music.service.MusicService;
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
public class MusicController {

    @Autowired
    private MusicService musicService;

    @GetMapping("/music/emotion")
    public List<Music> getMusicByEmotion(@RequestParam String emotionResult) {
        String query = "";
        switch(emotionResult) {
            case "슬픔":
                query = "조용한 발라드"; // 음악 검색조건: 슬픔
                break;
            case "화남":
                query = "록 음악"; // 음악 검색조건: 화남
                break;
            case "무표정":
            case "행복":
                query = "즐거운 팝 음악"; // 음악 검색조건: 무표정, 행복
                break;
            case "놀람":
            case "역겨움":
            case "두려운":
                query = "클래식 음악"; // 음악 검색조건: 놀람, 역겨움, 두려운
                break;
            default:
                return new ArrayList<>();
        }
        return musicService.getMusicContentsByQuery(query);
    }
}
