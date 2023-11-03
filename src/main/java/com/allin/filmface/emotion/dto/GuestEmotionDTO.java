package com.allin.filmface.emotion.dto;

import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class GuestEmotionDTO {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int guestEmotionNo;

    private String guestEmotionResult;
    private String guestEmotionAge;
    private String guestEmotionGender;
}