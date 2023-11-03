package com.allin.filmface.choiceContents.talk.entity;

import com.allin.filmface.member.entity.Guest;
import com.allin.filmface.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JoinFormula;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "TalkRequest")
@Table(name = "TALK_REQUEST")
public class TalkRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TALK_REQUEST_NO")
    private long talkRequestNo;

    @Column(name = "USER_NO")
    private int userNo;

    @Column(name = "USER_NICKNAME")
    private String userNickname;

    @Column(name = "USER_TYPE")
    private String userType;

    @Column(name = "REQUEST_WRITE_DATE")
    private LocalDateTime requestWriteDate;

    @Column(name = "USER_MESSAGE")
    private String userMessage;

    @Column(name = "CHAT_REQUEST_COUNT")
    private int chatRequestCount;

    @OneToOne(mappedBy = "talkRequest", fetch = FetchType.LAZY)
    @JsonBackReference
    private TalkResponse talkResponse;
}