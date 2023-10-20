package com.allin.filmface.niceTable.service;

import com.allin.filmface.choiceContents.youtube.entity.Youtube;
import com.allin.filmface.member.entity.Member;
import com.allin.filmface.member.repository.MemberRepository;
import com.allin.filmface.niceTable.dto.YoutubeNiceDTO;
import com.allin.filmface.niceTable.entity.Nice;
import com.allin.filmface.niceTable.entity.YoutubeNice;
import com.allin.filmface.niceTable.repository.NiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class NiceService {

    private final NiceRepository niceRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public NiceService(NiceRepository niceRepository, MemberRepository memberRepository) {
        this.niceRepository = niceRepository;
        this.memberRepository = memberRepository; //주입
    }


    public void createOrCancelYoutubeNice(YoutubeNiceDTO youtubeNiceDTO) {
        List<YoutubeNice> existingNices =
                niceRepository.findByMember_MemberNoAndYoutube_YoutubeNo(youtubeNiceDTO.getMember().getMemberNo(),
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
            youtubeNice.setYoutube(youtube);

            niceRepository.save(youtubeNice);
        }
    }

    public Long getNiceCountForYoutube(int youtubeNo) {
        return niceRepository.countByYoutube_YoutubeNo(youtubeNo);
    }

    public List<Youtube> getRecommendedVideosByAge(int memberNo) {
        Member member = memberRepository.findById(memberNo)
                .orElseThrow(() -> new EntityNotFoundException("Member not found with id: " + memberNo));

        // 회원의 나이대를 기준으로 추천 동영상을 조회하는 로직을 작성하세요.
        // 나이대 정보는 member.getMemberAge()를 사용하여 구할 수 있습니다.

        // 예시: 나이대를 기준으로 동영상을 추천하는 로직
        // List<Youtube> recommendedVideos = youtubeRepository.findRecommendedVideosByAge(member.getMemberAge());

        // 실제로 사용할 데이터베이스와 비즈니스 로직에 맞게 구현하세요.

        return Collections.emptyList(); // 추천 동영상 목록을 반환합니다.
    }
}