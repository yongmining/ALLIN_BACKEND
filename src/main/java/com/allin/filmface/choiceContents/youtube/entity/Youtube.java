//package com.allin.filmface.choiceContents.youtube.entity;
//
//import com.allin.filmface.choiceContents.youtube.dto.YoutubeDTO;
//import com.fasterxml.jackson.annotation.JsonBackReference;
//import lombok.*;
//import org.hibernate.annotations.Comment;
//
//import javax.persistence.*;
//
//@Entity(name = "Youtube")
//@Table(name = "youtube")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//public class Youtube {
//
//    @Id
//    @Column(name = "YOUTUBE_NO")
//    private int youtubeNo;
//
//    @Column(name = "YOUTUBE_LINK")
//    private String youtubeLink;
//
//    @Column(name = "YOUTUBE_VIEWS")
//    private int youtubeViews;
//
//
//    @Column(name = "YOUTUBE_TITLE")
//    private String youtubeTitle;
//
////        @ManyToOne
////    @JoinColumn(name = "EMOTION_NO", referencedColumnName = "EMOTION_NO")
////    @JsonBackReference
////    private Emotion emotion;
//
//
//}