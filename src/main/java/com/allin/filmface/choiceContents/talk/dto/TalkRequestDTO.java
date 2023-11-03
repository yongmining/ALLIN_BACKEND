package com.allin.filmface.choiceContents.talk.dto;

import com.allin.filmface.member.dto.GuestDTO;
import com.allin.filmface.member.dto.MemberSimpleDTO;
import lombok.*;
import org.springframework.web.context.annotation.SessionScope;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@SessionScope
public class TalkRequestDTO {
    private long talkRequestNo;

    private int userNo;
    private String userNickname;
    private String userType;

    private LocalDateTime requestWriteDate;
    private String userMessage;
    private int chatRequestCount;

    private TalkResponseDTO responseNo;
}