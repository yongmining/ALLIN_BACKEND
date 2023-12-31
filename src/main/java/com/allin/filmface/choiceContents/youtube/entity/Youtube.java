package com.allin.filmface.choiceContents.youtube.entity;

import com.allin.filmface.emotion.entity.Emotion;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "Youtube")
@Table(name = "youtube" )
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Youtube implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "YOUTUBE_NO")
    private int youtubeNo;

    @Column(name = "YOUTUBE_LINK")
    private String youtubeLink;


    @Column(name = "YOUTUBE_TITLE")
    private String youtubeTitle;

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