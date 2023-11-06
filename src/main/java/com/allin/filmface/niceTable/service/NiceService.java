package com.allin.filmface.niceTable.service;

import com.allin.filmface.choiceContents.youtube.dto.YoutubeDTO;
import com.allin.filmface.choiceContents.youtube.entity.Youtube;
import com.allin.filmface.choiceContents.youtube.repository.YoutubeRepository;
import com.allin.filmface.member.dto.MemberSimpleDTO;
import com.allin.filmface.member.entity.Member;
import com.allin.filmface.member.repository.MemberRepository;
import com.allin.filmface.niceTable.dto.YoutubeNiceDTO;
import com.allin.filmface.niceTable.entity.YoutubeNice;
import com.allin.filmface.niceTable.repository.NiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NiceService {

    private final NiceRepository niceRepository;
    private final MemberRepository memberRepository;

    private final YoutubeRepository youtubeRepository;

    @Autowired
    public NiceService(NiceRepository niceRepository, MemberRepository memberRepository, YoutubeRepository youtubeRepository) {
        this.niceRepository = niceRepository;
        this.memberRepository = memberRepository; //주입
        this.youtubeRepository = youtubeRepository;
    }


    @Transactional
    public void createOrCancelYoutubeNice(YoutubeNiceDTO youtubeNiceDTO) {
        // 1. Member 정보 체크
        if (youtubeNiceDTO.getMember() == null) {
            throw new IllegalArgumentException("멤버 정보가 없습니다.");
        }

        // 1.1 Member 엔터티 조회
        Integer memberNo = youtubeNiceDTO.getMember().getMemberNo();
        Member memberEntity = memberRepository.findById(memberNo)
                .orElseThrow(() -> new RuntimeException("멤버를 찾을 수 없습니다."));

        List<YoutubeNice> existingNices = niceRepository.findByMember_MemberNoAndYoutube_YoutubeLink(
                memberNo,
                youtubeNiceDTO.getYoutubeLink()
        );

        if (!existingNices.isEmpty()) {
            // 기존에 좋아요를 눌렀던 기록이 있다면 좋아요 정보 삭제
            niceRepository.deleteAll(existingNices);
        } else {
            YoutubeNice youtubeNice = new YoutubeNice();

            // Youtube 엔터티 검색
            Youtube youtubeEntity = youtubeRepository.findByYoutubeLinkAndMemberNo(youtubeNiceDTO.getYoutubeLink(), memberNo)
                    .orElseThrow(() -> new RuntimeException("해당 링크에서는 유튜브를 찾을 수 없습니다."));

            // YoutubeNice의 멤버 설정
            youtubeNice.setMember(memberEntity);
            youtubeNice.setYoutube(youtubeEntity);

            // 나머지 필드 값 설정
            youtubeNice.setNiceNo(youtubeNiceDTO.getNiceNo());

            // YoutubeNice 저장
            niceRepository.save(youtubeNice);
        }

        // 실시간으로 Youtube 테이블의 niceCount를 업데이트
        Long currentNiceCount = niceRepository.countByYoutube_YoutubeLink(youtubeNiceDTO.getYoutubeLink());
        youtubeRepository.updateNiceCountByLink(youtubeNiceDTO.getYoutubeLink(), currentNiceCount.intValue());
    }
    public Long getNiceCountForYoutube(String youtubeLink) {
        return niceRepository.countByYoutube_YoutubeLink(youtubeLink);
    }

//    public List<YoutubeDTO> getRecommendedVideosByEmotionAndAge(int memberNo) {
//        Member member = memberRepository.findById(memberNo).orElse(null);
//        if (member == null) {
//            // 회원 정보가 없을 경우 처리 로직
//            return Collections.emptyList();
//        }
//
//        int memberAge = member.getMemberAge();
//        int ageRangeStart = memberAge / 10 * 10;
//        int ageRangeEnd = ageRangeStart + 9;
//
//        List<Object[]> results = niceRepository.findTop6YoutubeByEmotionAndAge(memberNo, ageRangeStart, ageRangeEnd);
//
//        // Object[] 결과를 YoutubeDTO로 변환
//        return results.stream()
//                .map(result -> {
//                    YoutubeDTO dto = new YoutubeDTO();
//                    dto.setYoutubeNo((Integer) result[0]);
//                    dto.setYoutubeLink((String) result[1]);
//                    dto.setYoutubeTitle((String) result[2]);
//                    dto.setThumbnailUrl((String) result[3]);
//                    dto.setNiceCount(((Long) result[4]).intValue());
//                    dto.setEmotionNo((Integer) result[8]); // emotionNo 설정
//
//                    MemberSimpleDTO memberDTO = new MemberSimpleDTO();
//                    memberDTO.setMemberNo((int) result[5]);
//                    memberDTO.setMemberGender((String) result[6]);
//                    memberDTO.setMemberAge((Integer) result[7]);
//                    dto.setMember(memberDTO);
//
//                    return dto;
//                })
//                .collect(Collectors.toList());
//    }

    public List<YoutubeDTO> getRecommendedVideosByEmotionAndAge(int memberNo) {
        // 상수를 선언하여 의미를 명확하게 합니다.
        final int PAGE_SIZE = 6;

        return memberRepository.findById(memberNo)
                .map(member -> {
                    int memberAge = member.getMemberAge();
                    int ageRangeStart = memberAge / 10 * 10;
                    int ageRangeEnd = ageRangeStart + 9;

                    Pageable topVideos = PageRequest.of(0, PAGE_SIZE);
                    List<Object[]> results = niceRepository.findTopYoutubeByEmotionAndAge(memberNo, ageRangeStart, ageRangeEnd, topVideos);

                    return results.stream()
                            .map(result -> {
                                YoutubeDTO dto = new YoutubeDTO();
                                dto.setYoutubeNo((Integer) result[0]);
                                dto.setYoutubeLink((String) result[1]);
                                dto.setYoutubeTitle((String) result[2]);
                                dto.setThumbnailUrl((String) result[3]);
                                dto.setNiceCount(((Long) result[4]).intValue());

                                MemberSimpleDTO memberDTO = new MemberSimpleDTO();
                                memberDTO.setMemberNo((int) result[5]);
                                memberDTO.setMemberGender((String) result[6]);
                                memberDTO.setMemberAge((Integer) result[7]);
                                dto.setEmotionNo((Integer) result[8]);
                                dto.setMember(memberDTO);

                                return dto;
                            })
                            .collect(Collectors.toList());
                })
                .orElse(Collections.emptyList()); // Optional의 orElse를 사용하여 회원 정보가 없을 경우 빈 리스트를 반환
    }

}