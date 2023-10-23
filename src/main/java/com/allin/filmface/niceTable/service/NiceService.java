package com.allin.filmface.niceTable.service;

import com.allin.filmface.choiceContents.youtube.entity.Youtube;
import com.allin.filmface.choiceContents.youtube.repository.YoutubeRepository;
import com.allin.filmface.member.entity.Member;
import com.allin.filmface.member.repository.MemberRepository;
import com.allin.filmface.niceTable.dto.YoutubeNiceDTO;
import com.allin.filmface.niceTable.entity.Nice;
import com.allin.filmface.niceTable.entity.YoutubeNice;
import com.allin.filmface.niceTable.repository.NiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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


    public void createOrCancelYoutubeNice(YoutubeNiceDTO youtubeNiceDTO) {
        List<YoutubeNice> existingNices =
                niceRepository.findByMember_MemberNoAndYoutubeNo_YoutubeNo(youtubeNiceDTO.getMember().getMemberNo(),
                        youtubeNiceDTO.getYoutubeNo());

        if (!existingNices.isEmpty()) {
            // 기존의 모든 중복된 데이터를 삭제
            niceRepository.deleteAll(existingNices);
        } else {
            YoutubeNice youtubeNice = new YoutubeNice();

            // YoutubeNice 데이터 설정 로직
            Member member = new Member();
            member.setMemberNo(youtubeNiceDTO.getMember().getMemberNo());
            // 여기에 member의 다른 필드들을 youtubeNiceDTO로부터 설정하는 코드를 추가하실 수 있습니다.

            Youtube youtube = new Youtube();
            youtube.setYoutubeNo(youtubeNiceDTO.getYoutubeNo());
            // 여기에 youtube의 다른 필드들을 youtubeNiceDTO로부터 설정하는 코드를 추가하실 수 있습니다.

            youtubeNice.setMember(member);
            youtubeNice.setYoutubeNo(youtube);

            niceRepository.save(youtubeNice);
        }
    }

    public Long getNiceCountForYoutube(int youtubeNo) {
        return niceRepository.countByYoutubeNo_YoutubeNo(youtubeNo);
    }

    public List<Youtube> getRecommendedVideosByAge(int memberNo) {
        Member member = memberRepository.findById(memberNo).orElse(null);
        if (member == null) {
            return new ArrayList<>();
        }

        long age = member.getMemberAge();
        String ageGroup;
        if (age < 20) {
            ageGroup = "teen";
        } else if (age < 30) {
            ageGroup = "twenties";
        } else if (age < 40) {
            ageGroup = "thirties";
        } else {
            ageGroup = "older";
        }

        // ageGroup를 기반으로 youtubenice 테이블과 Member 테이블을 JOIN하여 연령대별로 가장 많은 좋아요를 받은 유튜브 영상을 조회
        return niceRepository.findTopVideosByAgeGroup(ageGroup);

    }

    public Long getTotalNiceCountByYoutubeNo(int youtubeNo) {
        return youtubeRepository.findTotalNiceCountByYoutubeNo(youtubeNo);
    }

}