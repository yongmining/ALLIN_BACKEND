package com.allin.filmface.common.paging;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ResponseDTOWithPaging {

    private Object data;
    private SelectCriteria pageInfo;
}
