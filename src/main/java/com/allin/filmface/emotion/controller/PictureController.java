//package com.allin.filmface.emotion.controller;
//
//import com.allin.filmface.emotion.dto.EmotionDTO;
//import com.allin.filmface.emotion.entity.Picture;
//import com.allin.filmface.picture.entity.service.PictureService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.server.ResponseStatusException;
//import org.springframework.http.HttpStatus;
//import java.util.NoSuchElementException;
//
//@RestController
//@RequiredArgsConstructor
//public class PictureController {
//
//    private final PictureService pictureService;
//
//    @GetMapping("/api/v1/pictures/{id}")
//    public ResponseEntity<Picture> getPicture(RequestBody EmotionDTO emotionDTO) {
//        try {
//            // PictureService 클래스의 findById() 메서드를 호출하여 이미지를 조회합니다.
//            Picture picture = pictureService.findById(id);
//
//            // 조회된 이미지를 ResponseEntity 객체에 담아서 반환합니다.
//            return ResponseEntity.ok(picture);
//        } catch (NoSuchElementException e) {
//            // 이미지가 존재하지 않을 경우 예외 처리
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Picture not found.");
//        }
//    }
//}