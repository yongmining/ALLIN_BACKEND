package com.allin.filmface.choiceContents.youtube.service;

import com.allin.filmface.choiceContents.youtube.dto.YoutubeDTO;
import com.allin.filmface.choiceContents.youtube.entity.Youtube;
import com.allin.filmface.choiceContents.youtube.repository.YoutubeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YoutubeService {

    private final YoutubeRepository youtubeRepository;

    @Autowired
    public YoutubeService(YoutubeRepository youtubeRepository) {
        this.youtubeRepository = youtubeRepository;
    }

    public YoutubeDTO addYoutubeContent(YoutubeDTO youtubeDTO) {
        Youtube youtube = new Youtube();
        // youtubeDTO의 정보를 youtube Entity로 옮김
        // 예: youtube.setYoutubeLink(youtubeDTO.getYoutubeLink());
        // 필요한 내용을 맞춰 변환합니다.

        youtube = youtubeRepository.save(youtube);
        // youtube Entity를 다시 youtubeDTO로 옮김
        // 예: youtubeDTO.setYoutubeLink(youtube.getYoutubeLink());
        // 필요한 내용을 맞춰 변환합니다.
        return youtubeDTO;
    }
}
