package com.allin.filmface.emotion.controller;

import com.allin.filmface.common.ResponseDTO;
import com.allin.filmface.emotion.dto.EmotionDTO;
import com.allin.filmface.emotion.service.EmotionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.util.List;

@Api(tags = "감정분석 관련 API")
@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class EmotionController {

    private final EmotionService emotionService;

    @ApiOperation("얼굴 사진의 감정분석")
    @PostMapping("/emotion")
    @CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}, exposedHeaders = "Content-Type")
    public ResponseEntity<ResponseDTO> analyzeEmotion(@RequestBody EmotionDTO emotionDTO) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        List<EmotionDTO> emotions = emotionService.analyzeEmotion(emotionDTO);

        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.OK, "분석성공", emotions);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(responseDTO);
    }
}