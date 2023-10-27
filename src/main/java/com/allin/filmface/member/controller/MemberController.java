package com.allin.filmface.member.controller;

import com.allin.filmface.common.ResponseDTO;
import com.allin.filmface.common.paging.Pagenation;
import com.allin.filmface.common.paging.ResponseDTOWithPaging;
import com.allin.filmface.common.paging.SelectCriteria;
import com.allin.filmface.member.dto.GuestDTO;
import com.allin.filmface.member.dto.MemberDTO;
import com.allin.filmface.member.dto.MemberSimpleDTO;
import com.allin.filmface.member.entity.Guest;
import com.allin.filmface.member.entity.Member;
import com.allin.filmface.member.service.MemberService;
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
import java.util.HashMap;
import java.util.Map;

@Api(tags = "멤버 관련 API")
@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @ApiOperation(value = "소셜Id로 멤버조회 임시")
    @GetMapping("/member/{social_Login}/{social_Id}")
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

    @ApiOperation(value = "토큰으로 회원 조회 임시")
    @GetMapping("/guests/{socialCode}")
    public ResponseEntity<ResponseDTO> findMemberByToken(@PathVariable String socialCode) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        Guest guestMember = memberService.findGuestMemberByCode(socialCode);
//        GuestDTO guestMember = memberService.findGuestById(guestNo);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("guestmember", guestMember);
        System.out.println(guestMember);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "토큰 비회원 조회 성공", responseMap));
    }

    @ApiOperation("게스트 No로 멤버조회")
    @GetMapping("/guest/{guestNo}")
    public ResponseEntity<ResponseDTO> findGuestById(@PathVariable int guestNo) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        GuestDTO guestMember = memberService.findGuestById(guestNo);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("guestmember", guestMember);
        System.out.println(guestMember);

        return ResponseEntity.ok().headers(headers).body(new ResponseDTO(HttpStatus.OK, "비회원 조회 성공", responseMap));
    }


    @ApiOperation("게스트 프로필 수정")
    @PutMapping("/guest/{guestNo}/profile")
    public ResponseEntity<ResponseDTO> updateProfile(@PathVariable int guestNo,
                                                     @RequestBody GuestDTO guestDTO) {

        GuestDTO guestProfile = memberService.guestprofile(guestNo, guestDTO);
        if (guestProfile != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDTO(HttpStatus.OK, "게스트 프로필 수정 성공", guestProfile));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO(HttpStatus.BAD_REQUEST, "게스트 프로필 수정 실패", null));
        }
    }
    @DeleteMapping("/guest/{guestNo}/delete")
    public ResponseEntity<?> deleteGuest(@PathVariable int guestNo) {

        memberService.deleteGuest(guestNo);

        return ResponseEntity.noContent().build();
    }
}