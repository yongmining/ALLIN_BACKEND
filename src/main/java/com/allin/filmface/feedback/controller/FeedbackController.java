package com.allin.filmface.feedback.controller;


import com.allin.filmface.common.ResponseDTO;
import com.allin.filmface.common.paging.Pagenation;
import com.allin.filmface.common.paging.ResponseDTOWithPaging;
import com.allin.filmface.common.paging.SelectCriteria;
import com.allin.filmface.feedback.dto.FeedbackDTO;
import com.allin.filmface.feedback.service.FeedbackService;
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
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.Date;

@Api(tags = "피드백 관련 API")
@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    @ApiOperation(value = "모든 피드백 목록 조회")
    @GetMapping("/feedbacks")
    public ResponseEntity<ResponseDTO> findAllFeedback(@PageableDefault Pageable pageable) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        Page<FeedbackDTO> feedbacks = feedbackService.findAllFeedback(pageable);
        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(feedbacks);

        ResponseDTOWithPaging data = new ResponseDTOWithPaging(feedbacks.getContent(), selectCriteria);

        return new ResponseEntity<>(new ResponseDTO(HttpStatus.OK, "조회성공", data), headers, HttpStatus.OK);
    }


    @ApiOperation("피드백 코드로 조회")
    @GetMapping("/feedback/{feedbackNo}")
    public ResponseEntity<ResponseDTO> findFeedbackById(@PathVariable long feedbackNo) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));


        return ResponseEntity.ok()
                .headers(headers)
                .body(new ResponseDTO(HttpStatus.OK, "조회성공", feedbackService.findFeedbackById(feedbackNo)));
    }

    @ApiOperation("멤버 번호로 작성한 피드백 조회")
    @GetMapping("/feedback/member/{memberNo}")
    public ResponseEntity<ResponseDTO> findFeedbacksByMemberNo(@PathVariable int memberNo) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        return ResponseEntity.ok()
                .headers(headers)
                .body(new ResponseDTO(HttpStatus.OK, "조회성공", feedbackService.findFeedbacksByMemberNo(memberNo)));
    }



    @ApiOperation("피드백 작성")
    @PostMapping("/feedback")
    public ResponseEntity<ResponseDTO> registNewFeedback(@RequestBody FeedbackDTO newFeedback) {
        System.out.println("Received Feedback: " + newFeedback.toString());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        newFeedback.setFeedbackDate(LocalDate.now());
        FeedbackDTO savedFeedback = feedbackService.createFeedback(newFeedback);

        return ResponseEntity.ok().headers(headers).body(new ResponseDTO(HttpStatus.OK, "생성성공", savedFeedback));
    }
}