package com.allin.filmface.emotion.controller;

import com.allin.filmface.emotion.dto.EmotionDTO;
import com.allin.filmface.emotion.dto.PictureDTO;
import com.allin.filmface.emotion.entity.Picture;
import com.allin.filmface.emotion.service.PictureService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterizedTypeReference;

import java.io.File;
import java.io.IOException;
import java.util.*;


@RestController
@RequestMapping("/api/v1")
public class PictureController {

    private final PictureService pictureService;

    public PictureController(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    @GetMapping("/picture/{id}")
    public ResponseEntity<Picture> getPicture(@PathVariable Long id) {
        Picture picture = pictureService.findById(id);
        return ResponseEntity.ok(picture);
    }

    @PostMapping("/picture/upload")
    public ResponseEntity<Map<String, Object>> uploadAndAnalyze(@RequestParam("file") MultipartFile multipartFile) {
        try {
            // 파일을 저장할 디렉토리 경로
            String uploadDirectory = "C:\\temp\\";

            // 원본 파일 이름
            String originalFileName = multipartFile.getOriginalFilename();

            // .jpg 확장자를 추가한 저장 파일 이름
            String fileNameWithExtension = originalFileName + ".jpg";

            // 저장할 파일 경로
            String filePath = uploadDirectory + fileNameWithExtension;

            File file = new File(filePath);
            multipartFile.transferTo(file);

            // 이미지 경로를 Flask 서버로 전송
            Map<String, Object> requestData = new HashMap<>();
            requestData.put("img_path", filePath); // 이미지 파일의 실제 경로를 설정
            requestData.put("actions", Arrays.asList("age", "gender", "emotion", "race"));

            // Define the REST endpoint URL for http://127.0.0.1:5000/analyze
            String serverUrl = "http://127.0.0.1:5000/analyze";

            // Create headers with Content-Type as application/json
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Send the JSON data to the Flask server
            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestData, headers);

            // Send the request to the Flask server
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange(serverUrl, HttpMethod.POST, requestEntity, new ParameterizedTypeReference<Map<String, Object>>() {});

            // Check the response status
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                System.out.println(responseEntity.getBody());
                // If the response is OK, you can get the JSON response as a map
                Map<String, Object> results = responseEntity.getBody();
                System.out.println(results);
                System.out.println(results.get("results"));

                EmotionDTO emotionDTOWithResults = new EmotionDTO();
                emotionDTOWithResults.setEmotionResult(results.get("dominant_emotion").toString());
                emotionDTOWithResults.setEmotionAge(results.get("age").toString());
                emotionDTOWithResults.setEmotionGender(results.get("dominant_gender").toString());
                PictureDTO pictureDTO = new PictureDTO();
                pictureDTO.setImage(multipartFile.getBytes() );
                return ResponseEntity.ok(results);

            } else {
                // Handle the case when the response is not OK
                System.out.println("Server returned an error response.");
                return ResponseEntity.internalServerError().build();
            }
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to analyze picture.", e);
        }
    }
}