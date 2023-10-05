package com.allin.filmface.choiceContents.youtube.dto;

import com.allin.filmface.emotion.dto.EmotionDTO;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class YoutubeDTO {

    private int youtubeNo;

    private  String youtubeLink;

    private  int youtubeViews;

    private String youtubeTitle;

    private List<EmotionDTO> emotionResult;

}
