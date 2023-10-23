package com.allin.filmface.choiceContents.exercise.entity;

import com.allin.filmface.emotion.entity.Emotion;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "Exercise")
@Table(name = "exercise")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EXERCISE_NO")
    private int exerciseNo;

    @Column(name = "EXERCISE_LINK")
    private String exerciseLink;



    @Column(name = "EXERCISE_TITLE")
    private String exerciseTitle;

    @Column(name = "THUMBNAIL_URL") // 썸네일 이미지 URL 컬럼 추가
    private String thumbnailUrl;



    @ManyToOne
    @JoinColumn(name = "EMOTION_NO", referencedColumnName = "EMOTION_NO")
    @JsonBackReference
    private Emotion emotion;

    private Integer memberNo;
}