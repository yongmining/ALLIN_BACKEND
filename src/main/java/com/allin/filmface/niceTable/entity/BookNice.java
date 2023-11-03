package com.allin.filmface.niceTable.entity;

import com.allin.filmface.choiceContents.book.entity.Book;
import com.allin.filmface.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.apache.ibatis.annotations.Many;
import org.hibernate.annotations.Comment;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity(name= "BookNice")
@Table(name = "BOOKNICE")
public class BookNice {

    @Id
    @GeneratedValue()
    @Column(name = "NICE_NO")
    @Comment("좋아요 번호")
    private int niceNo;

    @JoinColumn(name="BOOK_TITLE", referencedColumnName ="BOOK_TITLE")
    @OneToOne(fetch = FetchType.EAGER)
    private Book book;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MEMBER_NO", referencedColumnName = "MEMBER_NO")
    @JsonBackReference
    private Member member;



}
