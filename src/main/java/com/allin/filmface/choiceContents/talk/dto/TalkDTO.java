package com.allin.filmface.choiceContents.talk.dto;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class TalkDTO {

    private long talkNo;
    private String ChatUser;
    private String ChatBot;
    private LocalDate WriteDate;
    private String UserMessage;
    private String BotMessage;

}
