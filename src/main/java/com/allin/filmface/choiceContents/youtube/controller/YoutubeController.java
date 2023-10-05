//package com.allin.filmface.choiceContents.youtube.controller;
//
//import com.allin.filmface.choiceContents.youtube.dto.YoutubeDTO;
//import com.allin.filmface.choiceContents.youtube.service.YoutubeService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/youtube")
//public class YoutubeController {
//
//    @Autowired
//    private YoutubeService youtubeService;
//
//    @PostMapping("/searchAndSave")
//    public YoutubeDTO searchAndSave(@RequestParam String keyword) throws Exception {
//        return youtubeService.saveYoutubeDataFromSearch(keyword);
//    }
//
//}
