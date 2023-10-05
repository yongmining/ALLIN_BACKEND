package com.allin.filmface.choiceContents.youtube.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "youtube")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Youtube {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int youtubeNo;

    @Column(nullable = false)
    private String youtubeLink;

    @Column(nullable = false)
    private int youtubeCategory;

    @Column(nullable = false)
    private int youtubeViews;
}
