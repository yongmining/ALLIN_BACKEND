package com.allin.filmface.choiceContents.talk.controller;

import com.allin.filmface.choiceContents.talk.dto.TalkRequestDTO;
import com.allin.filmface.choiceContents.talk.dto.TalkResponseDTO;
import com.allin.filmface.choiceContents.talk.entity.TalkRequest;
import com.allin.filmface.choiceContents.talk.entity.TalkResponse;
import com.allin.filmface.choiceContents.talk.service.TalkService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@Api(tags = "소통 관련 API")
@RestController
@RequestMapping("/api/v1/talk")
@AllArgsConstructor
public class TalkController {

    private TalkService talkService;

    @PostMapping("/add")
    public TalkResponseDTO addTalk(@RequestBody TalkRequestDTO talkRequestDTO) {


        return talkService.addTalkRequestAndResponse(talkRequestDTO);
    }


    @GetMapping("/history/{userNo}")
    public ResponseEntity<List<TalkRequestDTO>> getTalkHistoryByUserNo(@PathVariable int userNo) {
        List<TalkRequest> talkHistory = talkService.getTalkHistoryByUserNo(userNo);

        // TalkRequest 엔티티를 TalkRequestDTO로 매핑 (DTO 클래스는 필요에 따라 생성)
        List<TalkRequestDTO> talkHistoryDTO = talkHistory.stream()
                .map(this::mapTalkRequestToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(talkHistoryDTO);
    }

    private TalkRequestDTO mapTalkRequestToDTO(TalkRequest talkRequest) {
        TalkRequestDTO dto = new TalkRequestDTO();
        dto.setTalkRequestNo(talkRequest.getTalkRequestNo());
        dto.setUserNo(talkRequest.getUserNo());
        dto.setUserNickname(talkRequest.getUserNickname());
        dto.setUserType(talkRequest.getUserType());
        dto.setRequestWriteDate(talkRequest.getRequestWriteDate());
        dto.setUserMessage(talkRequest.getUserMessage());

        // Response 엔티티를 DTO로 매핑
        if (talkRequest.getTalkResponse() != null) {
            TalkResponse talkResponse = talkRequest.getTalkResponse();
            TalkResponseDTO responseDTO = new TalkResponseDTO();
            responseDTO.setTalkResponseNo(talkResponse.getTalkResponseNo());
            responseDTO.setResponseWriteDate(talkResponse.getResponseWriteDate());
            responseDTO.setBotMessage(talkResponse.getBotMessage());
            responseDTO.setRequestNo(talkRequest.getTalkRequestNo());
            dto.setResponseNo(responseDTO);
        }

        return dto;
    }
}

