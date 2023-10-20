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


    @ManyToOne
    @JoinColumn(name = "PICTURE_NO", referencedColumnName = "PICTURE_NO")
    @JsonBackReference
    private Picture picture;


}
