package com.allin.filmface.niceTable.controller;

import com.allin.filmface.choiceContents.youtube.dto.YoutubeDTO;
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

    @PostMapping("/createOrCancelNice")
    public ResponseEntity<String> createOrCancelNice(@RequestBody YoutubeNiceDTO youtubeniceDTO) {
        niceService.createOrCancelYoutubeNice(youtubeniceDTO);
        System.out.println("Received member_No: " + youtubeniceDTO.getMember().getMemberNo());
        return new ResponseEntity<>("Nice created or canceled successfully", HttpStatus.OK);
    }

    @GetMapping("/nicecount/{youtubeLink}")
    public ResponseEntity<Long> getNiceCount(@PathVariable String youtubeLink) {
        Long count = niceService.getNiceCountForYoutube(youtubeLink);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @GetMapping("/recommendations/emotion-age/{memberNo}")
    public ResponseEntity<List<YoutubeDTO>> getRecommendedVideosByEmotionAndAge(@PathVariable int memberNo) {
        List<YoutubeDTO> recommendedVideos = niceService.getRecommendedVideosByEmotionAndAge(memberNo);
        System.out.println("Recommended videos count: " + recommendedVideos.size());
        return new ResponseEntity<>(recommendedVideos, HttpStatus.OK);
    }
    // YOUTUBE




}
