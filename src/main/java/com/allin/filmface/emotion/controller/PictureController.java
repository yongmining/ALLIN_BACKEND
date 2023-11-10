package com.allin.filmface.emotion.controller;

import com.allin.filmface.emotion.dto.EmotionDTO;
import com.allin.filmface.emotion.dto.PictureDTO;
import com.allin.filmface.emotion.entity.Emotion;  // Add this import
import com.allin.filmface.emotion.entity.Picture;
import com.allin.filmface.emotion.service.PictureService;
import com.allin.filmface.emotion.service.EmotionService;
import com.allin.filmface.member.controller.MemberController;
import com.allin.filmface.member.dto.MemberDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.io.File;
import java.util.*;

@RestController
@RequestMapping("/api/v1")
public class PictureController {

    private final PictureService pictureService;
    private final MemberController memberController;
    private final EmotionService emotionService;

    public PictureController(PictureService pictureService, MemberController memberController, EmotionService emotionService) {
        this.pictureService = pictureService;
        this.memberController = memberController;
        this.emotionService = emotionService;
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
            String uploadDirectory = "C:\\temp\\";
            String originalFileName = multipartFile.getOriginalFilename();
            String fileNameWithExtension = originalFileName + ".jpg";
            String filePath = uploadDirectory + fileNameWithExtension;

            File file = new File(filePath);
            multipartFile.transferTo(file);

            Map<String, Object> requestData = new HashMap<>();
            requestData.put("img_path", filePath);
            requestData.put("actions", Arrays.asList("age", "gender", "emotion", "race"));

            String serverUrl = "http://127.0.0.1:5000/analyze";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestData, headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange(serverUrl, HttpMethod.POST, requestEntity, new ParameterizedTypeReference<Map<String, Object>>() {});

            HttpStatus responseStatus = responseEntity.getStatusCode();
            if (responseStatus != HttpStatus.OK) {
                throw new ResponseStatusException(responseStatus, "Invalid response from Flask server.");
            }

            Map<String, Object> results = responseEntity.getBody();
            Object result = results.get("results");
            System.out.println(result);
            if (result instanceof List) {
                Map<String, Object> emotionMap = (Map<String, Object>) ((List<?>) result).get(0);
                System.out.println(result);
                String dominantEmotion = (String) emotionMap.get("dominant_emotion");
                int emotionAge = (int) emotionMap.get("age");
                String emotionGender = (String) emotionMap.get("dominant_gender");

                // Create an EmotionDTO object and set its properties
                EmotionDTO emotionDTO = new EmotionDTO();
                emotionDTO.setEmotionResult(dominantEmotion);
                emotionDTO.setEmotionAge(String.valueOf(emotionAge));
                emotionDTO.setEmotionGender(emotionGender);
                System.out.println(dominantEmotion);
                System.out.println(String.valueOf(emotionAge));
                System.out.println(emotionGender);

                // Save Emotion and get the saved EmotionDTO
                Emotion savedEmotion = emotionService.saveEmotion(emotionDTO);
                EmotionDTO savedEmotionDTO = new EmotionDTO();
                savedEmotionDTO.setEmotionNo((long) savedEmotion.getEmotionNo());
                savedEmotionDTO.setEmotionResult(savedEmotion.getEmotionResult());
                savedEmotionDTO.setEmotionAge(savedEmotion.getEmotionAge());
                savedEmotionDTO.setEmotionGender(savedEmotion.getEmotionGender());

                // Creating PictureDTO and saving to the database
                PictureDTO pictureDTO = new PictureDTO();
                pictureDTO.setImageName(originalFileName);

                // Assuming memberController.getLoggedInMemberNo() returns a MemberDTO
                Long loggedInMember = (long) memberController.getLoggedInMemberNo();

                if (loggedInMember != null) {
                    pictureDTO.setMemberNo(loggedInMember);
                }

                // Set the saved EmotionDTO to the PictureDTO
                pictureDTO.setEmotion(savedEmotionDTO);

                // Save the PictureDTO to the database
                pictureService.savePicture(pictureDTO, savedEmotionDTO);

                // Return success response
                Map<String, Object> response = new HashMap<>();
                response.put("message", "File uploaded and analyzed successfully.");
                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
            // Handle exceptions and return appropriate response
            e.printStackTrace(); // Log the exception for debugging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "Internal Server Error"));
        }
        return null;
    }
}