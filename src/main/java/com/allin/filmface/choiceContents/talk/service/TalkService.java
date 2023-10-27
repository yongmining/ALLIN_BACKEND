package com.allin.filmface.choiceContents.talk.service;

import com.allin.filmface.choiceContents.talk.dto.TalkDTO;
import com.allin.filmface.choiceContents.talk.entity.Talk;
import com.allin.filmface.choiceContents.talk.repository.TalkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class TalkService {

    private final TalkRepository talkRepository;

    @Value("${openai.apiKey}")
    private String openaiApiKey;

    @Autowired
    public TalkService(TalkRepository talkRepository) {
        this.talkRepository = talkRepository;
    }

    public TalkDTO addTalk(TalkDTO talkDTO) {
        // DTO를 엔티티로 변환하여 저장
        Talk talk = new Talk();
        talk.setChatUser(talkDTO.getChatUser());
        talk.setChatBot(talkDTO.getChatBot());
        talk.setWriteDate(talkDTO.getWriteDate());
        talk.setUserMessage(talkDTO.getUserMessage());
        talk.setBotMessage(talkDTO.getBotMessage());

        Talk savedTalk = talkRepository.save(talk);

        // 저장된 엔티티를 DTO로 변환하여 반환
        return convertToDTO(savedTalk);
    }

    public String callOpenAIChatAPI(String Message) {
        RestTemplate rt = new RestTemplate();

        // OpenAI API에 대화 요청 보내기
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", "Bearer " + openaiApiKey);

        Map<String, Object> requestBody = new HashMap<>();
        List<Map<String, String>> messages = new ArrayList<>();

        // 요청 데이터 구성
        Map<String, String> userMessageMap = new HashMap<>();
        userMessageMap.put("role", "user");
        userMessageMap.put("content", Message);
        messages.add(userMessageMap);

        requestBody.put("messages", messages);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> responseEntity = rt.exchange(
                "http://localhost:5050/chat",
                HttpMethod.POST,
                requestEntity,
                Map.class
        );

        Map<String, Object> responseMap = responseEntity.getBody();
        if (responseMap != null && responseMap.containsKey("botMessage") && responseMap.containsKey("writeDate")) {
            String botMessage = (String) responseMap.get("botMessage");
            String writeDate = (String) responseMap.get("writeDate");

            return botMessage;
        }

        return null; // 실패 시 null 반환 또는 예외 처리
    }


    public List<TalkDTO> getAllTalks() {
        // 모든 대화를 엔티티로부터 조회
        List<Talk> allTalks = talkRepository.findAll();

        // 엔티티 리스트를 DTO 리스트로 변환하여 반환
        return allTalks.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private TalkDTO convertToDTO(Talk talk) {
        TalkDTO talkDTO = new TalkDTO();
        talkDTO.setTalkNo(talk.getTalkNo());
        talkDTO.setChatUser(talk.getChatUser());
        talkDTO.setChatBot(talk.getChatBot());
        talkDTO.setWriteDate(talk.getWriteDate());
        talkDTO.setUserMessage(talk.getUserMessage());
        talkDTO.setBotMessage(talk.getBotMessage());
        return talkDTO;
    }
}


