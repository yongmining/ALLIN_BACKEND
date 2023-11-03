package com.allin.filmface.niceTable.service;

import com.allin.filmface.choiceContents.music.dto.MusicDTO;
import com.allin.filmface.choiceContents.music.entity.Music;
import com.allin.filmface.choiceContents.music.repository.MusicRepository;
import com.allin.filmface.member.dto.MemberSimpleDTO;
import com.allin.filmface.member.entity.Member;
import com.allin.filmface.member.repository.MemberRepository;
import com.allin.filmface.niceTable.dto.MusicNiceDTO;
import com.allin.filmface.niceTable.entity.MusicNice;
import com.allin.filmface.niceTable.repository.MusicNiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class musicNiceService {

    private final MusicNiceRepository musicNiceRepository;
    private final MemberRepository memberRepository;
    private final MusicRepository musicRepository;

    @Autowired
    public musicNiceService(MusicNiceRepository musicNiceRepository, MemberRepository memberRepository, MusicRepository musicRepository) {
        this.musicNiceRepository = musicNiceRepository;
        this.memberRepository = memberRepository;
        this.musicRepository = musicRepository;
    }

    @Transactional
    public void createOrCancelMusicNice(MusicNiceDTO musicNiceDTO) {
        if (musicNiceDTO.getMember() == null) {
            throw new IllegalArgumentException("멤버 정보가 없습니다.");
        }

        Integer memberNo = musicNiceDTO.getMember().getMemberNo();
        Member memberEntity = memberRepository.findById(memberNo)
                .orElseThrow(() -> new RuntimeException("멤버를 찾을 수 없습니다."));

        List<MusicNice> existingNices = musicNiceRepository.findByMember_MemberNoAndMusic_MusicLink(
                memberNo,
                musicNiceDTO.getMusicLink()
        );

        if (!existingNices.isEmpty()) {
            musicNiceRepository.deleteAll(existingNices);
        } else {
            MusicNice musicNice = new MusicNice();
            Music musicEntity = musicRepository.findByMusicLinkAndMemberNo(musicNiceDTO.getMusicLink(), memberNo)
                    .orElseThrow(() -> new RuntimeException("해당 링크에서는 음악을 찾을 수 없습니다."));

            musicNice.setMember(memberEntity);
            musicNice.setMusic(musicEntity);

            musicNice.setNiceNo(musicNiceDTO.getNiceNo());

            musicNiceRepository.save(musicNice);
        }

        Long currentNiceCount = musicNiceRepository.countByMusic_MusicLink(musicNiceDTO.getMusicLink());
        musicRepository.updateNiceCountByLink(musicNiceDTO.getMusicLink(), currentNiceCount.intValue());
    }

    public Long getNiceCountForMusic(String musicLink) {
        return musicNiceRepository.countByMusic_MusicLink(musicLink);
    }

    public List<MusicDTO> getRecommendedMusicsByEmotionAndAge(int memberNo) {
        Member member = memberRepository.findById(memberNo).orElse(null);
        if (member == null) {
            return Collections.emptyList();
        }

        int memberAge = member.getMemberAge();
        int ageRangeStart = memberAge / 10 * 10;
        int ageRangeEnd = ageRangeStart + 9;

        List<Object[]> results = musicNiceRepository.findTop6MusicByEmotionAndAge(memberNo, ageRangeStart, ageRangeEnd);

        return results.stream()
                .map(result -> {
                    MusicDTO dto = new MusicDTO();
                    dto.setMusicNo((Integer) result[0]);
                    dto.setMusicLink((String) result[1]);
                    dto.setMusicTitle((String) result[2]);
                    dto.setThumbnailUrl((String) result[3]);
                    dto.setNiceCount(((Long) result[4]).intValue());

                    MemberSimpleDTO memberDTO = new MemberSimpleDTO();
                    memberDTO.setMemberNo((int) result[5]);
                    memberDTO.setMemberGender((String) result[6]);
                    memberDTO.setMemberAge((Integer) result[7]);
                    dto.setMember(memberDTO);

                    return dto;
                })
                .collect(Collectors.toList());
    }
}
