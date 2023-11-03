package com.allin.filmface.choiceContents.youtube.entity;

import com.allin.filmface.emotion.entity.GuestEmotion;
import com.allin.filmface.member.entity.Guest;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "GuestYoutube")
@Table(name = "GUEST_YOUTUBE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GuestYoutube {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GUESTYOUTUBE_NO")
    private int guestYoutubeNo;

    @Column(name = "GUESTYOUTUBE_LINK")
    private String guestYoutubeLink;



    @Column(name = "GUESTYOUTUBE_TITLE")
    private String guestYoutubeTitle;

    @Column(name = "THUMBNAIL_URL") // 썸네일 이미지 URL 컬럼 추가
    private String thumbnailUrl;


    @ManyToOne
    @JoinColumn(name = "GUESTEMOTION_NO", referencedColumnName = "GUESTEMOTION_NO")
    @JsonBackReference
    private GuestEmotion guestEmotion;

    @Column(name = "GUEST_NO")
    private Integer guestNo;



}