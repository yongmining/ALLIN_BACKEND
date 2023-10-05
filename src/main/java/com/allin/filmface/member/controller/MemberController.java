package com.allin.filmface.member.controller;

import com.allin.filmface.common.ResponseDTO;
import com.allin.filmface.member.dto.MemberDTO;
import com.allin.filmface.member.dto.MemberSimpleDTO;
import com.allin.filmface.member.entity.Member;
import com.allin.filmface.member.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "멤버 관련 API")
@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @ApiOperation(value = "소셜Id로 멤버조회")
    @GetMapping("/member/{socialLogin}/{socialId}")
    public ResponseEntity<ResponseDTO> findBySocialId(@PathVariable String social_Login,
                                                      @PathVariable String social_Id) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        Member foundMember = memberService.findBySocialId(social_Login, social_Id);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("member", foundMember);

        return ResponseEntity.ok().headers(headers).body(new ResponseDTO(HttpStatus.OK, "소셜 아이디 검색 성공", responseMap));
    }

    @ApiOperation("멤버No로 멤버조회")
    @GetMapping("/member/{memberNo}")
    public ResponseEntity<ResponseDTO> findMemberById(@PathVariable long memberNo) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        MemberDTO foundMember = memberService.findMemberById(memberNo);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("members", foundMember);

        return ResponseEntity.ok().headers(headers).body(new ResponseDTO(HttpStatus.OK, "조회성공", responseMap));
    }

    @ApiOperation("프로필 수정")
    @PutMapping("/member/{memberNo}/profile")
    public ResponseEntity<ResponseDTO> updateProfile(@PathVariable long memberNo,
                                                     @RequestBody MemberSimpleDTO memberSimpleDTO) {

        MemberDTO updatedProfile = memberService.updateprofile(memberNo, memberSimpleDTO);
        if(updatedProfile != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDTO(HttpStatus.OK, "프로필 수정 성공", updatedProfile));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO(HttpStatus.BAD_REQUEST, "프로필 수정 실패", null));
        }
    }


    @DeleteMapping("/member/{memberNo}")
    public ResponseEntity<?> deleteMember(@PathVariable long memberNo) {

        memberService.deleteMember(memberNo);

        return ResponseEntity.noContent().build();
    }
}