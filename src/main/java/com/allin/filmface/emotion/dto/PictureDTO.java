package com.allin.filmface.emotion.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class PictureDTO {
    private int pictureNo;

    public int getPictureNo() {
        return pictureNo;
    }

    public void setPictureNo(int pictureNo) {
        this.pictureNo = pictureNo;
    }
}