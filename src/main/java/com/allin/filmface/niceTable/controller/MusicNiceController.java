package com.allin.filmface.niceTable.controller;

import com.allin.filmface.choiceContents.music.dto.MusicDTO;
import com.allin.filmface.niceTable.dto.MusicNiceDTO;
import com.allin.filmface.niceTable.service.musicNiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class MusicNiceController {

    private final musicNiceService musicNiceService;

    @Autowired
    public MusicNiceController(musicNiceService musicNiceService) {
        this.musicNiceService = musicNiceService;
    }

    @PostMapping("/createOrCancelMusicNice")
    public ResponseEntity<String> createOrCancelNice(@RequestBody MusicNiceDTO musicNiceDTO) {
        musicNiceService.createOrCancelMusicNice(musicNiceDTO);
        System.out.println("Received member_No: " + musicNiceDTO.getMember().getMemberNo());
        return new ResponseEntity<>("Nice created or canceled successfully", HttpStatus.OK);
    }

    @GetMapping("/nicecount/{musicLink}")
    public ResponseEntity<Long> getNiceCount(@PathVariable String musicLink) {
        Long count = musicNiceService.getNiceCountForMusic(musicLink);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @GetMapping("/musicrecommendations/emotionage/{memberNo}")
    public ResponseEntity<List<MusicDTO>> getRecommendedMusicByEmotionAndAge(@PathVariable int memberNo) {
        List<MusicDTO> recommendedMusic = musicNiceService.getRecommendedMusicsByEmotionAndAge(memberNo);
        System.out.println("Recommended music count: " + recommendedMusic.size());
        return new ResponseEntity<>(recommendedMusic, HttpStatus.OK);
    }
}