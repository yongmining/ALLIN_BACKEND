package com.allin.filmface.emotion.controller;

import com.allin.filmface.emotion.dto.PictureDTO;
import com.allin.filmface.emotion.entity.Picture;
import com.allin.filmface.emotion.service.PictureService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@Api(tags = "감정분석 관련 API")
@RestController
@CrossOrigin(origins = {"http://127.0.0.1:3000", "http://127.0.0.1:5000"}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}, exposedHeaders = "Content-Type")
@RequestMapping("/api/v1/")
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
            // Create a PictureDTO and populate it if needed
            PictureDTO pictureDTO = new PictureDTO();
            pictureDTO.setImageName(file.getOriginalFilename());

            // Call the service to save the picture
            Picture savedPicture = pictureService.save(pictureDTO, file.getBytes());

            return ResponseEntity.status(HttpStatus.CREATED).body(savedPicture);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to upload picture.", e);
        }
    }
}
