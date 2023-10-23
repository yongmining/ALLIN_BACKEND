package com.allin.filmface.member.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.allin.filmface.common.ResponseDTO;
import com.allin.filmface.common.paging.Pagenation;
import com.allin.filmface.common.paging.ResponseDTOWithPaging;
import com.allin.filmface.common.paging.SelectCriteria;
import com.allin.filmface.jwt.JwtTokenProvider;
import com.allin.filmface.member.dto.MemberDTO;
import com.allin.filmface.member.dto.MemberSimpleDTO;
import com.allin.filmface.member.entity.Member;
import com.allin.filmface.member.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "멤버 관련 API")
@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

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
    public ResponseEntity<ResponseDTO> findMemberById(@PathVariable int memberNo) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        MemberDTO foundMember = memberService.findMemberById(memberNo);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("members", foundMember);

        return ResponseEntity.ok().headers(headers).body(new ResponseDTO(HttpStatus.OK, "조회성공", responseMap));
    }

    @GetMapping("/members")
    public ResponseEntity<ResponseDTO> findAllMembers(@PageableDefault Pageable pageable) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        Page<MemberDTO> memberPage = memberService.findAllMembers(pageable);

        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(memberPage);

        ResponseDTOWithPaging data = new ResponseDTOWithPaging(memberPage.getContent(), selectCriteria);

        return ResponseEntity.ok().headers(headers).body(new ResponseDTO(HttpStatus.OK, "멤버페이지 조회 성공", data));
    }


    @ApiOperation("프로필 수정")
    @PutMapping("/member/{memberNo}/profile")
    public ResponseEntity<ResponseDTO> updateProfile(@PathVariable int memberNo,
                                                     @RequestBody MemberSimpleDTO memberSimpleDTO) {

        MemberDTO updatedProfile = memberService.updateprofile(memberNo, memberSimpleDTO);
        if (updatedProfile != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDTO(HttpStatus.OK, "프로필 수정 성공", updatedProfile));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO(HttpStatus.BAD_REQUEST, "프로필 수정 실패", null));
        }
    }


    @DeleteMapping("/member/{memberNo}")
    public ResponseEntity<?> deleteMember(@PathVariable int memberNo) {

        memberService.deleteMember(memberNo);

        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "토큰으로 회원 조회")
    @GetMapping("/member/{token}")
    public ResponseEntity<ResponseDTO> findMemberByToken(@RequestHeader("Authorization") String token) {
        if (jwtTokenProvider.validateToken(token)) {
            MemberDTO foundMember = memberService.getMemberByToken(token);

            if (foundMember != null) {
                Map<String, Object> responseMap = new HashMap<>();
                responseMap.put("member", foundMember);

                return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "회원 조회 성공", responseMap));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다.", null));
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseDTO(HttpStatus.UNAUTHORIZED, "토큰이 유효하지 않습니다.", null));
        }
    }
}