package com.allin.filmface.emotion.controller;

import com.allin.filmface.emotion.dto.PictureDTO;
import com.allin.filmface.emotion.entity.Picture;
import com.allin.filmface.emotion.service.PictureService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

import com.fasterxml.jackson.core.type.TypeReference;

@Api(tags = "감정분석 관련 API")
@RestController
@CrossOrigin(origins = {"http://127.0.0.1:3000", "http://127.0.0.1:5000"}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}, exposedHeaders = "Content-Type")
@RequestMapping("/api/v1")
@AllArgsConstructor
public class PictureController {

    private final PictureService pictureService;

    @GetMapping("/picture/{id}")
    public ResponseEntity<Picture> getPicture(@PathVariable Long id) {
        Picture picture = pictureService.findById(id);
        return ResponseEntity.ok(picture);
    }

    @PostMapping("/picture/upload")
    public ResponseEntity<Picture> uploadPicture(@RequestParam("file") MultipartFile file) {
        try {
            // 이미지를 저장하고 반환
            PictureDTO pictureDTO = new PictureDTO();
            pictureDTO.setImageName(file.getOriginalFilename());
            pictureDTO.setMemberNo(123);
            Picture savedPicture = pictureService.save(pictureDTO, file.getBytes());

            return ResponseEntity.status(HttpStatus.CREATED).body(savedPicture);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to upload picture.", e);
        }
    }

//    @PostMapping("/picture/analyze/{id}")
//    public ResponseEntity<Map<String, Object>> analyzePicture(@PathVariable Long id) {
//        try {
//            System.out.println("analyze picture------");
//            Picture pic = pictureService.findById(id);
//            byte[] img = pic.getImage();
//            // 이미지 파일을 가져오거나 저장된 경로를 얻어옵니다.
//            //String imagePath = "/users/hi/downloads/" + id + ".jpg"; // 이미지 경로를 얻는 방법에 따라 수정
//            //http://127.0.0.1:8080/api/v1/picture/upload
//            // Define the REST endpoint URL for http://127.0.0.1:5000/analyze
//            String serverUrl = "http://127.0.0.1:5000/analyze";
//
//            // Create headers with Content-Type as application/json
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_JSON);
//
//            // Create a map to include the image path and actions
//            Map<String, Object> requestData = new HashMap<>();
//            requestData.put("file", img);
//
//            // Add the list of actions
//            List<String> actions = Arrays.asList("age", "gender", "emotion", "race");
//            requestData.put("actions", actions);
//
//            // Convert the data to JSON format
//            ObjectMapper objectMapper = new ObjectMapper();
//            String jsonData = objectMapper.writeValueAsString(requestData);
//
//            // Create a request entity with the JSON data
//            HttpEntity<String> requestEntity = new HttpEntity<>(jsonData, headers);
//
//            // Send the JSON data to the server at http://127.0.0.1:5000/analyze
//            RestTemplate restTemplate = new RestTemplate();
//            ResponseEntity<String> responseEntity = restTemplate.exchange(serverUrl, HttpMethod.POST, requestEntity, String.class);
//
//            // Check the response status
//            if (responseEntity.getStatusCode() == HttpStatus.OK) {
//                // If the response is OK, you can get the JSON response as a string
//                String jsonResponse = responseEntity.getBody();
//
//                // Process the JSON result
//                ObjectMapper objectMapper1 = new ObjectMapper();
//                Map<String, Object> results = objectMapper1.readValue(jsonResponse, new TypeReference<Map<String, Object>>() {});
//
//                // Return the JSON result
//                return ResponseEntity.ok(results);
//            } else {
//                // Handle the case when the response is not OK
//                System.out.println("Server returned an error response.");
//                return ResponseEntity.internalServerError().build();
//            }
//        } catch (IOException e) {
//            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to analyze picture.", e);
//        }
//    }
}
