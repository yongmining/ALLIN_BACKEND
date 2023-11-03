package com.allin.filmface.choiceContents.talk.service;

import com.allin.filmface.choiceContents.talk.dto.TalkRequestDTO;
import com.allin.filmface.choiceContents.talk.dto.TalkResponseDTO;
import com.allin.filmface.choiceContents.talk.entity.TalkRequest;
import com.allin.filmface.choiceContents.talk.entity.TalkResponse;
import com.allin.filmface.choiceContents.talk.repository.TalkRequestRepository;
import com.allin.filmface.choiceContents.talk.repository.TalkResponseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class TalkService {

    private final TalkRequestRepository talkRequestRepository;
    private final TalkResponseRepository talkResponseRepository;

    @Transactional
    public TalkResponseDTO addTalkRequestAndResponse(TalkRequestDTO talkRequestDTO) {

        TalkResponseDTO response = callExternalService(talkRequestDTO);

        if (response != null && response.getBotMessage() != null) {
            TalkRequest talkRequest = saveTalkRequest(talkRequestDTO);
            saveTalkResponse(response, talkRequest);
            return response;
        } else {
            throw new RuntimeException("FastAPI로부터 응답을 받지 못했습니다.");
        }
    }

    private TalkResponseDTO callExternalService(TalkRequestDTO talkRequestDTO) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(
                "http://localhost:5050/chat",
                talkRequestDTO,
                TalkResponseDTO.class
        );
    }

    private TalkRequest saveTalkRequest(TalkRequestDTO talkRequestDTO) {
        TalkRequest talkRequest = mapDtoToTalkRequestEntity(talkRequestDTO);
        talkRequestRepository.save(talkRequest);
        return talkRequest;
    }

    private TalkRequest mapDtoToTalkRequestEntity(TalkRequestDTO talkRequestDTO) {
        TalkRequest talkRequest = new TalkRequest();
        talkRequest.setUserNickname(talkRequestDTO.getUserNickname());
        talkRequest.setUserType(talkRequestDTO.getUserType());
        talkRequest.setRequestWriteDate(LocalDateTime.now());
        talkRequest.setUserNo(talkRequestDTO.getUserNo());
        talkRequest.setUserMessage(talkRequestDTO.getUserMessage());
        talkRequest.setChatRequestCount(talkRequestDTO.getChatRequestCount());


        return talkRequest;
    }

    private void saveTalkResponse(TalkResponseDTO responseDTO, TalkRequest talkRequest) {
        TalkResponse talkResponse = new TalkResponse();
        talkResponse.setBotMessage(responseDTO.getBotMessage());
        talkResponse.setResponseWriteDate(LocalDateTime.now());
        talkResponse.setTalkRequest(talkRequest);

        talkResponseRepository.save(talkResponse);
    }


    public List<TalkRequest> getTalkHistoryByUserNo(int userNo) {
        List<TalkRequest> talkHistory = talkRequestRepository.findTalkRequestsWithResponseByUserNo(userNo);

        return talkHistory;
    }
}

