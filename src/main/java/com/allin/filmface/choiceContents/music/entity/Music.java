package com.allin.filmface.choiceContents.music.entity;

import com.allin.filmface.emotion.entity.Emotion;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "Music")
@Table(name = "music")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Music {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MUSIC_NO")
    private int musicNo;

    @Column(name = "MUSIC_LINK")
    private String musicLink;

    @Column(name = "MUSIC_TITLE")
    private String musicTitle;

    @Column(name = "THUMBNAIL_URL") // 썸네일 이미지 URL 컬럼 추가
    private String thumbnailUrl;


    @ManyToOne
    @JoinColumn(name = "EMOTION_NO", referencedColumnName = "EMOTION_NO")
    @JsonBackReference
    private Emotion emotion;

    @Column(name = "NICE_COUNT")
    private Integer niceCount = 0;
    public Integer getNiceCount() {
        return niceCount == null ? 0 : niceCount; // null 체크 추가
    }

    private Integer memberNo;
}
