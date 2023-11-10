package com.allin.filmface.emotion.service;

import com.allin.filmface.emotion.dto.EmotionDTO;
import com.allin.filmface.emotion.dto.PictureDTO;
import com.allin.filmface.emotion.entity.Picture;
import com.allin.filmface.emotion.repository.PictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PictureService {

    private final PictureRepository pictureRepository;

    @Autowired
    public PictureService(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }

    public Picture savePicture(PictureDTO pictureDTO, EmotionDTO emotionDTO) {
        // Create a Picture entity from the DTO
        Picture picture = new Picture();
        picture.setImageName(pictureDTO.getImageName());
        picture.setMemberNo(pictureDTO.getMemberNo());

        // Save the Emotion object
        picture.setEmotion(emotionDTO.getEmotion());

        // Save the entity to the database
        return pictureRepository.save(picture);
    }

    public Picture findById(Long id) {
        return pictureRepository.findById(id).orElse(null);
    }
}