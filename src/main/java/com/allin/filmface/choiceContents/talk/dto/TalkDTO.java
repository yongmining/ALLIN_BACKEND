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
    private String chatUser;
    private String chatBot;
    private LocalDate writeDate;
    private String userMessage;
    private String botMessage;

}
