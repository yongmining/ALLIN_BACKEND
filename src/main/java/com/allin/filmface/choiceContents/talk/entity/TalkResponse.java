package com.allin.filmface.choiceContents.talk.entity;

import com.allin.filmface.choiceContents.talk.dto.TalkRequestDTO;
import com.allin.filmface.member.dto.GuestDTO;
import com.allin.filmface.member.dto.MemberSimpleDTO;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "TalkResponse")
@Table(name = "TALK_RESPONSE")
public class TalkResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TALK_RESPONSE_NO")
    private long talkResponseNo;

    @Column(name = "RESPONSE_WRITE_DATE")
    private LocalDateTime responseWriteDate;

    @Column(name = "BOT_MESSAGE")
    private String botMessage;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TALK_REQUEST_NO")
    @JsonManagedReference
    private TalkRequest talkRequest;
}
