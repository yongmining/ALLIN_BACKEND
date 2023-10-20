//package com.allin.filmface.niceTable.entity;
//
//import lombok.*;
//import org.hibernate.annotations.Comment;
//
//import javax.persistence.*;
//import java.awt.print.Book;
//
//@AllArgsConstructor
//@NoArgsConstructor
//@Setter
//@Getter
//@ToString
//@Entity(name= "BookNice")
//@Table(name = "BOOKNICE")
//public class BookNice {
//
//    @Id
//    @GeneratedValue()
//    @Column(name = "NICE_NO")
//    @Comment("좋아요 번호")
//    private int niceNo;
//
//    @JoinColumn
//    @OneToOne(fetch = FetchType.EAGER)
//    private Book bookNo;
//
//    @OneToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "")
//
//
//}
