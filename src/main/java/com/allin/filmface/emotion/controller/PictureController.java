package com.allin.filmface.emotion.controller;

import com.allin.filmface.emotion.dto.EmotionDTO;
import com.allin.filmface.emotion.dto.PictureDTO;
import com.allin.filmface.emotion.entity.Emotion;
import com.allin.filmface.emotion.entity.Picture;
import com.allin.filmface.emotion.service.PictureService;
import org.modelmapper.ModelMapper;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class PictureController {

    private final PictureService pictureService;
    private final ModelMapper modelMapper;

    public PictureController(PictureService pictureService, ModelMapper modelMapper) {
        this.pictureService = pictureService;
        this.modelMapper = modelMapper;
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
            requestData.put("img_path", filePath);
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
            System.out.println("Response status code: " + responseEntity.getStatusCode());

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                Map<String, Object> results = responseEntity.getBody();
                Object result = results.get("results");
                if (result instanceof List) {
                    result = ((List<?>) result).get(0);
                }
                System.out.println(result);

                // ModelMapper를 사용하여 JSON 데이터를 EmotionDTO로 변환
                EmotionDTO emotionDTO = modelMapper.map(result, EmotionDTO.class);

                // PictureDTO 객체 생성 및 설정
                PictureDTO pictureDTO = new PictureDTO();
                pictureDTO.setImage(multipartFile.getBytes());

                // EmotionDTO를 엔티티 객체로 매핑
                Emotion emotion = modelMapper.map(emotionDTO, Emotion.class);

                // Picture 엔티티 생성
                Picture picture = new Picture();
                picture.setEmotion(emotion);
                picture.setPictureDTO(pictureDTO);

                // 이후 필요한 작업 수행 (예: 데이터베이스에 저장)
                pictureService.savePictureWithEmotion(picture);

                return ResponseEntity.ok(results);
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid response from Flask server.");
            }
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to analyze picture.", e);
        }
    }
}