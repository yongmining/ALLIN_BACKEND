package com.allin.filmface.emotion.controller;

import com.allin.filmface.common.ResponseDTO;
import com.allin.filmface.emotion.dto.EmotionDTO;
import com.allin.filmface.emotion.dto.PictureDTO;
import com.allin.filmface.emotion.entity.Emotion;
import com.allin.filmface.emotion.entity.Picture;
import com.allin.filmface.emotion.service.PictureService;
import com.allin.filmface.member.controller.MemberController;
import com.allin.filmface.member.dto.MemberDTO;
import com.allin.filmface.member.entity.Member;
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
    private final MemberController memberController; // MemberController 주입

    public PictureController(PictureService pictureService, MemberController memberController) {
        this.pictureService = pictureService;
        this.memberController = memberController; // MemberController 주입
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
                //Map<String, Object> results = responseEntity.getBody();
                //Map<String, String> result = (Map<String, String>) results.get("results");
                //Object result = results.get("results");
                Map<String, Object> results = responseEntity.getBody();
                Object result = results.get("results");
                System.out.println(results);
                if (result instanceof List) {
                result = ((List<?>) result).get(0);

                System.out.println(result);



                // Extract emotion data from the JSON response
                    // Extract emotion data from the JSON response
                String dominantEmotion = (String) ((Map<String, Object>) result).get("emotion.dominant_emotion");
                int emotionAge = (int) ((Map<String, Object>) result).get("emotion.age");
                String emotionGender = (String) ((Map<String, Object>) result).get("emotion.dominant_gender");

                // Create an EmotionDTO object and set its properties

                // Create an EmotionDTO object and set its propertiesD
                System.out.println("dominant_emotion: " + dominantEmotion);
                System.out.println("age: " + emotionAge);
                System.out.println("dominant_gender: " + emotionGender);
                EmotionDTO emotionDTO = new EmotionDTO();
                emotionDTO.setEmotionResult(dominantEmotion);
                emotionDTO.setEmotionAge(String.valueOf(emotionAge));
                emotionDTO.setEmotionGender(emotionGender);

                // Print the emotion result, emotion age, and emotion gender
                //System.out.println("EmotionResult = " + emotionDTO.getEmotionResult());
                //System.out.println("EmotionAge = " + emotionDTO.getEmotionAge());
                //System.out.println("EmotionGender = " + emotionDTO.getEmotionGender());

            }


            // 직접 딕셔너리 값에서 값을 추출하여 EmotionDTO에 설정

            // 멤버 정보 설정
            MemberDTO memberDTO = new MemberDTO();
            memberDTO.setSocialLogin("kakao");
            memberDTO.setSocialId("10");

            // 멤버 정보를 통해 Member 엔티티 검색
            //ResponseEntity<ResponseDTO> member = memberController.findBySocialId(memberDTO.getSocialLogin(), memberDTO.getSocialId);
            //Member memberEntity = member.getBody();

            // Picture 엔티티 생성
            Picture picture = new Picture();
            //picture.setMember(memberEntity);
            picture.setImageName(originalFileName);

            // 저장할 이미지 데이터 설정
            byte[] imageData = multipartFile.getBytes();
            picture.setImage(imageData);

            // 이후 필요한 작업 수행 (예: 데이터베이스에 저장)
            pictureService.savePictureWithEmotion(picture);

            return ResponseEntity.ok(results);
        } else {
            throw new ResponseStatusException(responseEntity.getStatusCode(), "Invalid response from Flask server.");
        }
    } catch (IOException e) {
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to analyze picture.", e);
    }
}

    private Map<String, Object> result() {
        return new HashMap<>();
    }
}