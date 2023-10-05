package com.allin.filmface.feedback.entity;

import com.allin.filmface.member.entity.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity(name = "Feedback")
@Table(name = "FEEDBACK")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FEEDBACK_NO")
    private long feedbackNo;

    @Column(name = "FEEDBACK_CONTENT")
    private String feedbackContent;

    @Column(name = "FEEDBACK_DATE", nullable = false)
    private LocalDate feedbackDate;

    @Column(name = "FEEDBACK_STAR")
    private int feedbackStar;

    @ManyToOne
    @JoinColumn(name = "MEMBER_NO", referencedColumnName = "MEMBER_NO")
    private Member member;

}
