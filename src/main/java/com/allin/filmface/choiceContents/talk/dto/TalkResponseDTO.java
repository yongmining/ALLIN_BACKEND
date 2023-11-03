package com.allin.filmface.choiceContents.talk.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class TalkResponseDTO {
    private long talkResponseNo;
    private LocalDateTime responseWriteDate;
    private String botMessage;

    private long requestNo;
//    private TalkRequestDTO requestNo;
}
