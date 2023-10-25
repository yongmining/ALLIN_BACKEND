package com.allin.filmface.choiceContents.book.dto;

import lombok.*;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class BookDTO {
    private int bookNo;
    private String title;
    private String subTitle;
    private String imageName;
    private String author;
    private String imageUrl; // 이미지 URL을 문자열로 저장
    private byte[] imageData; // 이미지 데이터를 byte 배열로 저장

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }
}
