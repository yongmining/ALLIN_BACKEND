package com.allin.filmface.choiceContents.exercise.entity;

import com.allin.filmface.emotion.entity.Emotion;
import com.allin.filmface.emotion.entity.GuestEmotion;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "GuestExercise")
@Table(name = "GUEST_EXERCISE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GuestExercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GUESTEXERCISE_NO")
    private int guestExerciseNo;

    @Column(name = "GUESTEXERCISE_LINK")
    private String guestExerciseLink;



    @Column(name = "GUESTEXERCISE_TITLE")
    private String guestExerciseTitle;

    @Column(name = "THUMBNAIL_URL") // 썸네일 이미지 URL 컬럼 추가
    private String thumbnailUrl;


    @ManyToOne
    @JoinColumn(name = "GUESTEMOTION_NO", referencedColumnName = "GUESTEMOTION_NO")
    @JsonBackReference
    private GuestEmotion guestEmotion;


    @Column(name = "GUEST_NO")
    private Integer guestNo;

}