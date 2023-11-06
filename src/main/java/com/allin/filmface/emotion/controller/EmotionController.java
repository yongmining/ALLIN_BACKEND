package com.allin.filmface.emotion.controller;

import com.allin.filmface.emotion.dto.EmotionDTO;
import com.allin.filmface.emotion.dto.PictureDTO;
import com.allin.filmface.emotion.entity.Picture;
import com.allin.filmface.emotion.service.EmotionService;
import com.allin.filmface.emotion.service.PictureService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;


@Api(tags = "감정분석 관련 API")
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class EmotionController {

    private final PictureService pictureService;
    private  final EmotionService emotionService;

    @GetMapping("/emotion/member/{memberNo}")
    public ResponseEntity<List<EmotionDTO>> getEmotionsByMemberNo(@PathVariable Integer memberNo) {
        List<EmotionDTO> emotions = emotionService.getEmotionsByMemberNo(memberNo);
        return ResponseEntity.ok(emotions);

    }

    @PostMapping("/emotion")
    @CrossOrigin(origins = {"http://127.0.0.1:3000", "http://127.0.0.1:5000"}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}, exposedHeaders = "Content-Type")
//    public ResponseEntity<EmotionDTO> analyzeEmotion(@RequestPart("emotionDTO") EmotionDTO emotionDTO, @RequestPart("file") MultipartFile file) throws Exception {
//        try {
//            // React에서 보낸 사진 파일을 PictureService를 사용하여 저장합니다.
//            PictureDTO pictureDTO = new PictureDTO();
//            pictureDTO.setImage(file.getBytes());
//            pictureDTO.setImageName(emotionDTO.getEmotionResult());
//
//            // Call the service to save the picture and get the updated PictureDTO
//            //Picture savedPictureDTO = pictureService.save(pictureDTO, file.getBytes());
//
//            // Flask 서버에 파일을 보내고 결과를 받습니다.
//            EmotionDTO emotionDTOWithResults = sendFileToFlask(savedPictureDTO);
//
//            if (emotionDTOWithResults != null) {
//                return ResponseEntity.ok(emotionDTOWithResults);
//            } else {
//                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to analyze emotion.");
//            }
//        } catch (IOException e) {
//            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to analyze emotion.", e);
//        }
//    }

    private EmotionDTO sendFileToFlask(Picture pictureDTO) throws IOException {
        // Flask 서버에 파일을 보내고 결과를 받는 로직입니다.
        URL url = new URL("http://127.0.0.1:5000/analyze");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/octet-stream");
        connection.setDoOutput(true);

        // InputStream에서 파일 데이터를 읽어옵니다.
        byte[] bytes = pictureDTO.getImage();

        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(bytes);
        outputStream.flush();

        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            // 성공
            InputStream inputStream = connection.getInputStream();
            byte[] responseBytes = inputStream.readAllBytes();

            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> resultMap = mapper.readValue(new String(responseBytes), new TypeReference<Map<String, String>>() {
            });

            // Create a new EmotionDTO object with the updated e motion results
            EmotionDTO emotionDTOWithResults = new EmotionDTO();

            emotionDTOWithResults.setEmotionResult(resultMap.get("dominant_emotion"));
            emotionDTOWithResults.setEmotionAge(resultMap.get("age"));
            emotionDTOWithResults.setEmotionGender(resultMap.get("dominant_gender"));

            return emotionDTOWithResults;
        } else {
            // 실패
            return null;
        }
    }
}