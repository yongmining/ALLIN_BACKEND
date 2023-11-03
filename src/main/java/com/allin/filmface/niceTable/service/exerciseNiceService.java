package com.allin.filmface.niceTable.service;

import com.allin.filmface.choiceContents.exercise.dto.ExerciseDTO;
import com.allin.filmface.choiceContents.exercise.entity.Exercise;
import com.allin.filmface.choiceContents.exercise.repository.ExerciseRepository;
import com.allin.filmface.member.dto.MemberSimpleDTO;
import com.allin.filmface.member.entity.Member;
import com.allin.filmface.member.repository.MemberRepository;
import com.allin.filmface.niceTable.dto.ExerciseNiceDTO;
import com.allin.filmface.niceTable.entity.ExerciseNice;
import com.allin.filmface.niceTable.repository.ExerciseNiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class exerciseNiceService {

    private final ExerciseNiceRepository exerciseNiceRepository;
    private final MemberRepository memberRepository;
    private final ExerciseRepository exerciseRepository;

    @Autowired
    public exerciseNiceService(ExerciseNiceRepository exerciseNiceRepository, MemberRepository memberRepository, ExerciseRepository exerciseRepository) {
        this.exerciseNiceRepository = exerciseNiceRepository;
        this.memberRepository = memberRepository;
        this.exerciseRepository = exerciseRepository;
    }

    @Transactional
    public void createOrCancelExerciseNice(ExerciseNiceDTO exerciseNiceDTO) {
        if (exerciseNiceDTO.getMember() == null) {
            throw new IllegalArgumentException("멤버 정보가 없습니다.");
        }

        Integer memberNo = exerciseNiceDTO.getMember().getMemberNo();
        Member memberEntity = memberRepository.findById(memberNo)
                .orElseThrow(() -> new RuntimeException("멤버를 찾을 수 없습니다."));

        List<ExerciseNice> existingNices = exerciseNiceRepository.findByMember_MemberNoAndExercise_ExerciseLink(
                memberNo,
                exerciseNiceDTO.getExerciseLink()
        );

        if (!existingNices.isEmpty()) {
            exerciseNiceRepository.deleteAll(existingNices);
        } else {
            ExerciseNice exerciseNice = new ExerciseNice();
            Exercise exerciseEntity = exerciseRepository.findByExerciseLinkAndMemberNo(exerciseNiceDTO.getExerciseLink(), memberNo)
                    .orElseThrow(() -> new RuntimeException("해당 링크에서는 운동을 찾을 수 없습니다."));

            exerciseNice.setMember(memberEntity);
            exerciseNice.setExercise(exerciseEntity);

            exerciseNice.setNiceNo(exerciseNiceDTO.getNiceNo());

            exerciseNiceRepository.save(exerciseNice);
        }

        Long currentNiceCount = exerciseNiceRepository.countByExercise_ExerciseLink(exerciseNiceDTO.getExerciseLink());
        exerciseRepository.updateNiceCountByLink(exerciseNiceDTO.getExerciseLink(), currentNiceCount.intValue());
    }

    public Long getNiceCountForExercise(String exerciseLink) {
        return exerciseNiceRepository.countByExercise_ExerciseLink(exerciseLink);
    }

    public List<ExerciseDTO> getRecommendedExercisesByEmotionAndAge(int memberNo) {
        Member member = memberRepository.findById(memberNo).orElse(null);
        if (member == null) {
            return Collections.emptyList();
        }

        int memberAge = member.getMemberAge();
        int ageRangeStart = memberAge / 10 * 10;
        int ageRangeEnd = ageRangeStart + 9;

        List<Object[]> results = exerciseNiceRepository.findTop6ExerciseByEmotionAndAge(memberNo, ageRangeStart, ageRangeEnd);

        return results.stream()
                .map(result -> {
                    ExerciseDTO dto = new ExerciseDTO();
                    dto.setExerciseNo((Integer) result[0]);
                    dto.setExerciseLink((String) result[1]);
                    dto.setExerciseTitle((String) result[2]);
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
