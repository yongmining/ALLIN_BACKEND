//package com.allin.filmface.emotion.service;
//
//import com.allin.filmface.common.ResponseDTO;
//import com.allin.filmface.common.paging.Pagenation;
//import com.allin.filmface.common.paging.ResponseDTOWithPaging;
//import com.allin.filmface.common.paging.SelectCriteria;
//import com.allin.filmface.emotion.EmotionDTO;
//import com.allin.filmface.emotion.service.EmotionService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.AllArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.web.PageableDefault;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.nio.charset.Charset;
//import java.util.List;
//
//@Api(tags = "감정분석 관련 API")
//@RestController
//@RequestMapping("/api/v1")
//@AllArgsConstructor
//public class EmotionController {
//
//    private final EmotionService emotionService;
//
//    @ApiOperation("얼굴 사진의 감정분석")
//    @PostMapping("/emotion")
//    public ResponseEntity<ResponseDTO> analyzeEmotion(@RequestBody EmotionDTO EmotionDTO) throws Exception {
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//
//        List<EmotionDTO> emotions = emotionService.analyzeEmotion(file);
//
//        return ResponseEntity.ok().headers(headers).body(new ResponseDTO(HttpStatus.OK, "분석성공", emotions));
//    }
//}