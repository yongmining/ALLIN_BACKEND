package com.allin.filmface.emotion.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity(name= "Emotion")
@Table(name = "EMOTION")
public class Emotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMOTION_NO")
    private int emotionNo;

    @Column(name = "EMOTION_RESULT")
    private String emotionResult;

    @Column(name = "MEMBER_NO")
    private Integer memberNo;

    @OneToOne
    @JoinColumn(name = "PICTURE_NO", referencedColumnName = "PICTURE_NO")
    @JsonBackReference
    private Picture picture;

    public void setEmotionAge(String emotionAge) {
    }

    public void setEmotionGender(String emotionGender) {
    }
    // 사진하나에 감정하나
    // Constructors, getters, and settersl

    //public void setEmotionResult(String emotionResult) {
    //    this.emotionResult = emotionResult;
    //}
}

