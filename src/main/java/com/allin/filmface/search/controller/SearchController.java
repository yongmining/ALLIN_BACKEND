package com.allin.filmface.search.controller;

import com.allin.filmface.common.ResponseDTO;

import com.allin.filmface.common.paging.Pagenation;
import com.allin.filmface.common.paging.ResponseDTOWithPaging;
import com.allin.filmface.common.paging.SelectCriteria;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.allin.filmface.search.dto.SearchDTO;
import com.allin.filmface.search.service.SearchService;

import java.nio.charset.Charset;

@Api(tags = "조회 관련 API")
@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @ApiOperation(value = "조회")
    @GetMapping("/search")
    public ResponseEntity<ResponseDTO> findAllSearch(@PageableDefault Pageable pageable, @RequestParam int memberNo) {
        System.out.println(memberNo);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        Page<SearchDTO> searchs = searchService.findAllSearch(pageable, memberNo);

        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(searchs);

        ResponseDTOWithPaging data = new ResponseDTOWithPaging(searchs.getContent(), selectCriteria);

        return new ResponseEntity<>(new ResponseDTO(HttpStatus.OK, "조회 성공", data), headers, HttpStatus.OK);
    }


}


