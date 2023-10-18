package com.allin.filmface.phrase.entity;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class Phrase {

    @Id
    @Column(name = "PHRASE_NO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int phraseNo;


    @Column(name = "PHRASE_CONTENT")
    private String phraseContent;

}
