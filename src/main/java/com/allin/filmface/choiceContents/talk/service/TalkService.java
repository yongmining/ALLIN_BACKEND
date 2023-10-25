package com.allin.filmface.choiceContents.talk.service;


import com.allin.filmface.choiceContents.talk.dto.TalkDTO;
import com.allin.filmface.choiceContents.talk.entity.Talk;
import com.allin.filmface.choiceContents.talk.repository.TalkRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class TalkService {

    private final TalkRepository talkRepository;

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


