package com.allin.filmface.choiceContents.youtube.controller;

import com.allin.filmface.choiceContents.youtube.dto.YoutubeDTO;
import com.allin.filmface.choiceContents.youtube.entity.Youtube;
import com.allin.filmface.choiceContents.youtube.service.YoutubeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/v1")
@AllArgsConstructor
@RestController
public class YoutubeController {

    @Autowired
    private YoutubeService youtubeService;

    @GetMapping("/youtube/emotion")
    public List<Youtube> getYoutubeContentsByEmotion(@RequestParam(required=false, defaultValue="DEFAULT_VALUE") String emotionResult) {
        return youtubeService.getYoutubeContentsByEmotion(emotionResult);
    }


}
