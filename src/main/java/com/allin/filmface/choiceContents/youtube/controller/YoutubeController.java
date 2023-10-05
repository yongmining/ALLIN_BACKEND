package com.allin.filmface.choiceContents.youtube.controller;

import com.allin.filmface.choiceContents.youtube.dto.YoutubeDTO;
import com.allin.filmface.choiceContents.youtube.service.YoutubeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/youtube")
public class YoutubeController {

    private final YoutubeService youtubeService;

    @Autowired
    public YoutubeController(YoutubeService youtubeService) {
        this.youtubeService = youtubeService;
    }

    @PostMapping
    public YoutubeDTO addYoutubeContent(@RequestBody YoutubeDTO youtubeDTO) {
        return youtubeService.addYoutubeContent(youtubeDTO);
    }

    // 필요한 다른 endpoint들도 이곳에 추가
}
