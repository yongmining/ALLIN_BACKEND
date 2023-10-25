package com.allin.filmface.phrase.entity;
import lombok.*;
import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class EmotionPhrase {
    @Id
    @Column(name = "EMOTIONPHRASE_NO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int emotionPhraseNo;


    @Column(name = "EMOTIONPHRASE_CONTENT")
    private String emotionPhraseContent;

}