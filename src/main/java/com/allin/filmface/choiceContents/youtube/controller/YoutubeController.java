package com.allin.filmface.choiceContents.youtube.controller;

import com.allin.filmface.choiceContents.youtube.dto.YoutubeDTO;
import com.allin.filmface.choiceContents.youtube.entity.Youtube;
import com.allin.filmface.choiceContents.youtube.service.YoutubeService;
import com.allin.filmface.emotion.entity.Emotion;
import com.allin.filmface.emotion.repository.EmotionRepository;
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
public class YoutubeController {

    @Autowired
    private YoutubeService youtubeService;
    @Autowired
    private EmotionRepository emotionRepository;

    @GetMapping("/youtube/emotion")
    public List<Youtube> getYoutubeVideosBasedOnEmotion(@RequestParam(name = "memberNo") Integer memberNo) {
        // 해당 memberNo의 마지막 emotionResult를 가져옵니다.
        Emotion recentEmotion = emotionRepository.findFirstByMemberNoOrderByEmotionNoDesc(memberNo);
        if (recentEmotion == null) {
            return new ArrayList<>();
        }

        String emotionResult = recentEmotion.getEmotionResult();

        String query = "";
        switch(emotionResult) {
            case "슬픔":
                query = "슬플 때 볼만한 영상";
                break;
            case "화남":
                query = "화날 때 볼만한 영상";
                break;
            case "무표정":
            case "행복":
                query = "재밌는 영상";
                break;
            case "놀람":
            case "역겨움":
            case "두려운":
                query = "차분한 영상";
                break;
            default:
                return new ArrayList<>();
        }
        return youtubeService.getYoutubeContentsByQuery(query, memberNo);
    }
}
