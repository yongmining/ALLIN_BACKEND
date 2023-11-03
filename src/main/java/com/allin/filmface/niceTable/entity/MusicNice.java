package com.allin.filmface.niceTable.entity;

import com.allin.filmface.choiceContents.music.entity.Music;
import com.allin.filmface.choiceContents.youtube.entity.Youtube;
import com.allin.filmface.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity(name="MusicNice")
@Table(name="MUSICNICE", uniqueConstraints = @UniqueConstraint(columnNames = {"MEMBER_NO", "MUSIC_LINK"}))
public class MusicNice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NICE_NO")
    @Comment("좋아요 번호")
    private int niceNo;

    @ManyToOne
    @JoinColumn(name = "MUSIC_LINK", referencedColumnName = "MUSIC_LINK")
    @JsonBackReference
    private Music music;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MEMBER_NO", referencedColumnName = "MEMBER_NO")
    @JsonBackReference
    private Member member;

}
