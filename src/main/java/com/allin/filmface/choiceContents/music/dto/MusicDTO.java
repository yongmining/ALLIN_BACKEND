package com.allin.filmface.choiceContents.music.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class MusicDTO {

    private int musicNo;

    private  long musicLink;

    private  int musicCategory;

    private  int musicViews;
}
