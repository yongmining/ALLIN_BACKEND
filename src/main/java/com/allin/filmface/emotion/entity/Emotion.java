package com.allin.filmface.emotion.entity;

import com.allin.filmface.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity(name = "Emotion")
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
    private String emotionAge;
    private String emotionGender;
    public Emotion(String emotionResult, String emotionAge, String emotionGender, Picture picture) {
    }


    public void setEmotionAge(String emotionAge) {
        this.emotionAge = emotionAge;
    }

    public void setEmotionGender(String emotionGender) {
        this.emotionGender = emotionGender;
    }


    public String getEmotionAge() {
        return this.emotionAge;
    }

    public String getEmotionGender() {
        return this.emotionGender;
    }

    public void setEmotionResult(String emotionResult) {
        this.emotionResult = emotionResult;
    }


    public void setMember(Member member) {
    }
}