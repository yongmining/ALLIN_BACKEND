package com.allin.filmface.niceTable.dto;

import com.allin.filmface.search.dto.SearchDTO;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class NiceDTO {

    private SearchDTO search;

    private int niceNo;
    private int niceCount;


}
