package com.allin.filmface.emotion.dto;

import com.allin.filmface.emotion.entity.Picture;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PictureDTO {

    private Long pictureNo;  // Use Long instead of int for ID
    private byte[] image;
    private String imageName;
    private Long memberNo;  // Use Long instead of int for memberNo

    private EmotionDTO emotion;  // Embed EmotionDTO in PictureDTO



    // Setter and Getter methods
}