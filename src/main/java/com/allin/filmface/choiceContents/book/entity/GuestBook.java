package com.allin.filmface.choiceContents.book.entity;


import com.allin.filmface.emotion.entity.GuestEmotion;
import lombok.*;

import javax.persistence.*;

@Entity(name = "GuestBook")
@Table(name = "guest_Book")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GuestBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GUESTBOOK_NO")
    private int guestBookNo;

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

    @Column(name = "GUESTEMOTION_RESULT")
    private String guestEmotionResult;

    @ManyToOne
    @JoinColumn(name = "GUESTEMOTION_NO", referencedColumnName = "GUESTEMOTION_NO")
    private GuestEmotion guestEmotion;



}