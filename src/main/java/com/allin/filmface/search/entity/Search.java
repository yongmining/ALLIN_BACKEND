package com.allin.filmface.search.entity;

import com.allin.filmface.emotion.entity.Emotion;
import com.allin.filmface.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.Comment;

import javax.persistence.*;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity(name= "Search")
@Table(name = "SEARCH")
public class Search {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEARCH_NO")
    @Comment("조회번호")
    private int searchNo;

    @Column(name = "CONTENT_NO")
    private int contentNo;

    @ManyToOne
    @JoinColumn(name = "EMOTION_NO", referencedColumnName = "EMOTION_NO")
    @JsonBackReference
    private Emotion emotion;

}
