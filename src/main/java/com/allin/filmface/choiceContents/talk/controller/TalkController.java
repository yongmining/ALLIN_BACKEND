package com.allin.filmface.choiceContents.talk.controller;

import com.allin.filmface.choiceContents.talk.dto.TalkDTO;
import com.allin.filmface.choiceContents.talk.service.TalkService;
import com.allin.filmface.common.ResponseDTO;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "소통 관련 API")
@RestController
@RequestMapping("/api/v1/talk")
@AllArgsConstructor
public class TalkController {

    private final TalkService talkService;

    @PostMapping("/add")
    public ResponseEntity<ResponseDTO> addTalk(@RequestBody TalkDTO talkDTO) {
        // DTO를 엔티티로 변환하여 저장
        TalkDTO addedTalk = talkService.addTalk(talkDTO);

        // 대화 데이터를 준비합니다.
        List<Map<String, String>> messages = new ArrayList<>();
        Map<String, String> userMessage = new HashMap<>();
        userMessage.put("role", "user");
        userMessage.put("content", talkDTO.getUserMessage());
        messages.add(userMessage);

        // OpenAI API를 호출하고 응답을 가져옵니다.
        String openAIResponse = talkService.callOpenAIChatAPI(messages.toString());

        // OpenAI 응답을 BotMessage에 설정합니다.
        addedTalk.setBotMessage(openAIResponse);

        // ResponseDTO를 생성하고 반환합니다.
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.OK, "대화가 추가되었습니다.", addedTalk);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        return ResponseEntity.ok().headers(headers).body(responseDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseDTO> getAllTalks() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        List<TalkDTO> allTalks = talkService.getAllTalks();
        return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK, "모든 대화 목록", allTalks));
    }

}
