package com.allin.filmface.choiceContents.youtube.dto;

import com.allin.filmface.search.dto.SearchDTO;
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


    private String youtubeTitle;

    private List<SearchDTO> searchNo;

}
