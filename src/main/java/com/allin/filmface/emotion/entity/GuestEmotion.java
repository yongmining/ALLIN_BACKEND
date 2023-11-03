package com.allin.filmface.emotion.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity(name= "GuestEmotion")
@Table(name = "GUESTEMOTION")
public class GuestEmotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GUESTEMOTION_NO")
    private int guestEmotionNo;

    @Column(name = "GUESTEMOTION_RESULT")
    private String guestEmotionResult;

    @Column(name = "GUEST_NO")
    private Integer guestNo;



}
