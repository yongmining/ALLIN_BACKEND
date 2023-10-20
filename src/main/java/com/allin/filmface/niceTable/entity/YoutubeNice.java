package com.allin.filmface.niceTable.entity;

import com.allin.filmface.choiceContents.youtube.entity.Youtube;
import com.allin.filmface.member.dto.MemberSimpleDTO;
import com.allin.filmface.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.apache.ibatis.annotations.One;
import org.hibernate.annotations.Comment;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity(name= "YoutubeNice")
@Table(name = "YOUTUBENICE", uniqueConstraints = @UniqueConstraint(columnNames = {"MEMBER_NO", "YOUTUBE_NO"}))
public class YoutubeNice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NICE_NO")
    @Comment("좋아요 번호")
    private int niceNo;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "YOUTUBE_NO", referencedColumnName = "YOUTUBE_NO")
    @JsonBackReference
    private Youtube youtube;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MEMBER_NO", referencedColumnName = "MEMBER_NO")
    @JsonBackReference
    private Member member;

}
