package com.allin.filmface.member.service;

import com.allin.filmface.member.dto.MemberDTO;
import com.allin.filmface.member.dto.MemberSimpleDTO;
import com.allin.filmface.member.entity.Member;
import com.allin.filmface.member.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public long registNewUser(MemberDTO newMember) {
        newMember.setMemberNickname("새로운 회원 " + (Math.random() * 100 + 1));
        return memberRepository.save(modelMapper.map(newMember, Member.class)).getMemberNo();
    }


    //일부 멤버 조회
    public MemberDTO findMemberById(int memberNo) {

        Member foundMember = memberRepository.findById(memberNo)
                .orElseThrow(() -> new NoSuchElementException("회원을 찾을 수 없습니다."));
        return modelMapper.map(foundMember, MemberDTO.class);
    }


    public Page<MemberDTO> findAllMembers(Pageable pageable) {
        Page<Member> memberPage = memberRepository.findAll(pageable);
        return memberPage.map(member -> modelMapper.map(member, MemberDTO.class));
    }

    @Transactional
    public MemberDTO updateprofile(int memberNo, MemberSimpleDTO updateMember) {

        Member member = memberRepository.findById(memberNo)
                 .orElseThrow(() -> new NoSuchElementException("수정 오류가 났습니다. 다시 수정 요청 부탁드립니다."));

        member.setMemberNickname(updateMember.getMemberNickname());
        member.setMemberAge(updateMember.getMemberAge());
        member.setMemberGender(updateMember.getMemberGender());

        return modelMapper.map(member, MemberDTO.class);
    }

    @Transactional
    public void deleteMember(int memberNo) {

        Member foundMember = memberRepository.findById(memberNo)
                .orElseThrow(() -> new NoSuchElementException("회원 정보를 찾을 수 없습니다."));

        memberRepository.delete(foundMember);
    }

    public Member findBySocialId(String socialLogin, String socialId) {
        return memberRepository.findBySocialId(socialLogin, socialId);
    }
}