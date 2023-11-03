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
    private String thumbnailUrl;
    private String author;
    private byte[] imageData; // 이미지 데이터를 저장하는 필드
    private String emotionResult;
    private Integer niceCount;


    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }
}
