package com.allin.filmface.choiceContents.talk.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Talk")
@Table(name = "TALK")
public class Talk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TALK_NO")
    private long talkNo;

    @Column(name = "CHAT_USER")
    private String ChatUser;

    @Column(name = "CHAT_BOT")
    private String ChatBot;

    @Column(name = "WRITE_DATE")
    private LocalDate WriteDate;

    @Column(name = "USER_MESSAGE")
    private String UserMessage;

    @Column(name = "BOT_MESSAGE")
    private String BotMessage;


}

