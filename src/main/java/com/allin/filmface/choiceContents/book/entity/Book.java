package com.allin.filmface.choiceContents.book.entity;

import com.allin.filmface.emotion.entity.Emotion;
import lombok.*;

import javax.persistence.*;

@Entity(name = "Book")
@Table(name = "book")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOOK_NO")
    private int bookNo;

    @Column(name = "BOOK_TITLE")
    private String bookTitle;

    @Column(name = "BOOK_SUBTITLE")
    private String bookSubTitle;

    @Column(name = "BOOK_IMAGE_NAME")
    private String bookImageName;

    @Column(name = "THUMBNAIL_URL")
    private String thumbnailUrl;

    @Column(name = "BOOK_AUTHOR")
    private String bookAuthor;

    @Column(name = "EMOTION_RESULT")
    private String emotionResult;

    @ManyToOne
    @JoinColumn(name = "EMOTION_NO", referencedColumnName = "EMOTION_NO")
    private Emotion emotion;



}