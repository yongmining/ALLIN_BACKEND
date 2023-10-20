package com.allin.filmface.niceTable.dto;

import com.allin.filmface.member.dto.MemberSimpleDTO;
import com.allin.filmface.search.dto.SearchDTO;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class MusicNiceDTO {

    private MemberSimpleDTO member;

    private int niceNo;
    private int musicno;


}
