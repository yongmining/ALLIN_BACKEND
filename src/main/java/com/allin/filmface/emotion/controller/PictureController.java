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
    @CrossOrigin(origins = {"http://127.0.0.1:3000", "http://127.0.0.1:5000"}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}, exposedHeaders = "Content-Type")
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
                Map<String, Object> results = responseEntity.getBody();
                Object result = results.get("results");
                if (result instanceof List) {
                    result = ((List<?>) result).get(0);
                }

                // Print the result.
                System.out.println(result);

                // ObjectMapper를 생성
                ObjectMapper objectMapper = new ObjectMapper();

                // JSON 데이터를 Map으로 변환
                Map<String, Object> resultMap = objectMapper.convertValue(result, Map.class);

                // EmotionDTO 객체 생성 및 설정
                EmotionDTO emotionDTOWithResults = new EmotionDTO();
                emotionDTOWithResults.setEmotionResult(resultMap.get("dominant_emotion").toString());
                emotionDTOWithResults.setEmotionAge(resultMap.get("age").toString());
                emotionDTOWithResults.setEmotionGender(resultMap.get("dominant_gender").toString());

                // PictureDTO 객체 생성 및 설정
                PictureDTO pictureDTO = new PictureDTO();
                pictureDTO.setImage(multipartFile.getBytes());

                // 이후 필요한 작업 수행 (예: 데이터베이스에 저장)
                // ModelMapper 빈 등록 파트와
                // EmotionDTO 및 PictureDTO를 데이터베이스에 저장
                pictureService.savePictureWithEmotion(emotionDTOWithResults, pictureDTO);

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