package com.allin.filmface.niceTable.controller;

import com.allin.filmface.choiceContents.youtube.entity.Youtube;
import com.allin.filmface.niceTable.dto.YoutubeNiceDTO;
import com.allin.filmface.niceTable.entity.Nice;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.allin.filmface.niceTable.service.NiceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class NiceController {

    private final NiceService niceService;

    @Autowired
    public NiceController(NiceService niceService) {
        this.niceService = niceService;
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createYoutubeNice(@RequestBody YoutubeNiceDTO youtubeNiceDTO) {
        System.out.println(youtubeNiceDTO);
        niceService.createOrCancelYoutubeNice(youtubeNiceDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping("/nicecount/{youtubeNo}")
    public ResponseEntity<Long> getNiceCount(@PathVariable int youtubeNo) {
        Long count = niceService.getNiceCountForYoutube(youtubeNo);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
//    @GetMapping("/recommendations/{memberNo}")
//    public ResponseEntity<List<Youtube>> getRecommendedVideosByAge(@PathVariable int memberNo) {
//        List<Youtube> recommendedVideos = niceService.getRecommendedVideosByAge(memberNo);
//        return new ResponseEntity<>(recommendedVideos, HttpStatus.OK);
//    }

    @GetMapping("/recommendations/emotion-age/{memberNo}")
    public ResponseEntity<List<Youtube>> getRecommendedVideosByEmotionAndAge(@PathVariable int memberNo) {
        List<Youtube> recommendedVideos = niceService.getRecommendedVideosByEmotionAndAge(memberNo);
        return new ResponseEntity<>(recommendedVideos, HttpStatus.OK);
    }

}
