package com.allin.filmface.common.paging;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SelectCriteria {
    private int maxPage;                //가장 마지막 페이지
    private int startPage;                //한 번에 보여줄 페이징 버튼의 시작하는 페이지 수
    private int endPage;                //한 번에 보여줄 페이징 버튼의 마지막 페이지 수
}