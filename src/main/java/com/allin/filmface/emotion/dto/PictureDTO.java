package com.allin.filmface.emotion.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PictureDTO {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pictureNo;

    private byte[] image;  // 변경: InputStream 대신 byte[] 사용
    private String imageName;
    private int memberNo;
}