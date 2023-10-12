package com.allin.filmface.choiceContents.youtube.entity;

import com.allin.filmface.choiceContents.youtube.dto.YoutubeDTO;
import com.allin.filmface.search.entity.Search;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.Comment;

import javax.persistence.*;

@Entity(name = "Youtube")
@Table(name = "youtube")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Youtube {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "YOUTUBE_NO")
    private int youtubeNo;

    @Column(name = "YOUTUBE_LINK")
    private String youtubeLink;



    @Column(name = "YOUTUBE_TITLE")
    private String youtubeTitle;


        @ManyToOne
    @JoinColumn(name = "SEARCH_NO", referencedColumnName = "SEARCH_NO")
    @JsonBackReference
    private Search search;


}