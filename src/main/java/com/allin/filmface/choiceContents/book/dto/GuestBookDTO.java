package com.allin.filmface.choiceContents.book.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class GuestBookDTO {
    private int guestBookNo;
    private String title;
    private String subTitle;
    private String imageName;
    private String thumbnailUrl;
    private String author;
    private byte[] imageData; // 이미지 데이터를 저장하는 필드
    private String guestEmotionResult;


    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }
}
