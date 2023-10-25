package com.allin.filmface.emotion.service;

import com.allin.filmface.emotion.dto.PictureDTO;
import com.allin.filmface.emotion.entity.Picture;
import com.allin.filmface.emotion.repository.PictureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PictureService {

    private final PictureRepository pictureRepository;

    public Picture findById(Long id) {
        return pictureRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Picture not found."));
    }

    public Picture save(PictureDTO pictureDTO, byte[] file) throws IOException {
        // Create a new Picture entity and set its properties
        Picture picture = new Picture();
        picture.setImageName(pictureDTO.getImageName());
        picture.setImage(pictureDTO.getImage());

        // Save the picture entity in the repository
        return pictureRepository.save(picture);
    }
}
