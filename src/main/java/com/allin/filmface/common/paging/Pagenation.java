package com.allin.filmface.common.paging;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class Pagenation {

    public static SelectCriteria getSelectCriteria(Page page) {

        int buttonAmount = 10;
        int maxPage = page.getTotalPages();            //전체 페이지에서 가장 마지막 페이지
        int startPage;            //한번에 표시될 페이지 버튼의 시작할 페이지
        int endPage;            //한번에 표시될 페이지 버튼의 끝나는 페이지

        startPage = (int) (Math.ceil((double) (page.getNumber() + 1) / buttonAmount) - 1) * buttonAmount + 1;

        endPage = startPage + buttonAmount - 1;

        if (maxPage < endPage) {
            endPage = maxPage;
        }

        if (maxPage == 0 && endPage == 0) {
            maxPage = startPage;
            endPage = startPage;
        }

        SelectCriteria selectCriteria = new SelectCriteria(maxPage, startPage, endPage);

        return selectCriteria;
    }

}