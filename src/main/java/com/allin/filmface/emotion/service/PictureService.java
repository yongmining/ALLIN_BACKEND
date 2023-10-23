//package com.allin.filmface.emotion.service;
//
//import com.allin.filmface.emotion.dto.PictureDTO;
//import com.allin.filmface.emotion.entity.Picture;
//import com.allin.filmface.emotion.repository.PictureRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.NoSuchElementException;
//
//@Service
//@RequiredArgsConstructor
//public class PictureService {
//
//    private final PictureRepository pictureRepository;
//
//    public Picture findById(Long id) {
//        return pictureRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Picture not found."));
//    }
//
//    public Picture save(PictureDTO pictureDTO, MultipartFile file) throws IOException {
//        Picture picture = new Picture();
//        picture.setPictureNo(pictureDTO.getPictureNo()); // Set the picture number as needed
//        picture.setName(file.getOriginalFilename());
//        picture.setContentType(file.getContentType());
//        picture.setData(file.getBytes());
//        // You may set other properties here based on your entity
//
//        return pictureRepository.save(picture);
//    }
//}