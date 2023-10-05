package com.allin.filmface.search.entity;

import com.allin.filmface.member.entity.Member;
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
public class SearchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEARCH_NO")
    @Comment("조회번호")
    private int searchNo;

    @Column(name = "SEARCH_AGE")
    private int searchAge;

    @Column(name = "TOTAL_NO")
    private int totalNo;

    @ManyToOne
    @JoinColumn(name= "MEMBER_NO", referencedColumnName = "MEMBER_NO" )
    @Comment("멤버아이디")
    private Member member;
}
