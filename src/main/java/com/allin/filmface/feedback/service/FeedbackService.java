package com.allin.filmface.feedback.service;

import com.allin.filmface.feedback.dto.FeedbackDTO;
import com.allin.filmface.feedback.entity.Feedback;
import com.allin.filmface.feedback.repository.FeedbackRepository;
import com.allin.filmface.member.entity.Member;
import com.allin.filmface.member.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    // 전체피드백 조회
    public Page<FeedbackDTO> findAllFeedback(Pageable page) {
        page = PageRequest.of(page.getPageNumber() <= 0 ? 0 : page.getPageNumber() - 1, page.getPageSize(),
                Sort.by("feedbackNo"));

        Page<Feedback> feedbacks = feedbackRepository.findAll(page);

        return feedbacks.map(feedback -> modelMapper.map(feedback, FeedbackDTO.class));
    }

    // 피드백 번호로 조회
    public FeedbackDTO findFeedbackById(long feedbackNo) { // 파라미터 타입 수정됨
        Feedback foundFeedback = feedbackRepository.findById(feedbackNo).orElseThrow(() -> new RuntimeException("Feedback not found"));

        return modelMapper.map(foundFeedback, FeedbackDTO.class);
    }

    // 멤버 번호로 작성한 피드백 조회
    public List<FeedbackDTO> findFeedbacksByMemberNo(int memberNo) {
        List<Feedback> feedbacks = feedbackRepository.findByMember_MemberNo(memberNo);
        return feedbacks.stream()
                .map(feedback -> modelMapper.map(feedback, FeedbackDTO.class))
                .collect(Collectors.toList());
    }



    // 피드백 작성
    @Transactional
    public FeedbackDTO createFeedback(FeedbackDTO newFeedback) {
        if (newFeedback.getMember() == null) {
            throw new IllegalArgumentException("Feedback의 회원 정보가 제공되지 않았습니다.");
        }

        Member member = memberRepository.findById(newFeedback.getMember().getMemberNo())
                .orElseThrow(() -> new IllegalArgumentException("회원 번호가 유효하지 않습니다."));

        Feedback feedback = modelMapper.map(newFeedback, Feedback.class);
        feedback.setMember(member);

        Feedback savedFeedback = feedbackRepository.save(feedback);
        return modelMapper.map(savedFeedback, FeedbackDTO.class);
    }
}



