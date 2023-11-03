package com.allin.filmface.niceTable.entity;

import com.allin.filmface.choiceContents.exercise.entity.Exercise;
import com.allin.filmface.choiceContents.music.entity.Music;
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
@Entity(name="ExerciseNice")
@Table(name="EXERCISENICE", uniqueConstraints = @UniqueConstraint(columnNames = {"MEMBER_NO", "EXERCISE_LINK"}))
public class ExerciseNice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NICE_NO")
    @Comment("좋아요 번호")
    private int niceNo;

    @ManyToOne
    @JoinColumn(name = "EXERCISE_LINK", referencedColumnName = "EXERCISE_LINK")
    @JsonBackReference
    private Exercise exercise;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MEMBER_NO", referencedColumnName = "MEMBER_NO")
    @JsonBackReference
    private Member member;

}
