//package com.allin.filmface.search.service;
//
//import com.allin.filmface.emotion.dto.EmotionDTO;
//import com.allin.filmface.member.dto.MemberSimpleDTO;
//import com.allin.filmface.member.repository.MemberRepository;
//import com.allin.filmface.search.dto.SearchDTO;
//import com.allin.filmface.search.entity.Search;
//import com.allin.filmface.search.repository.SearchRepository;
//import lombok.AllArgsConstructor;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.stereotype.Service;
//
//@Service
//@AllArgsConstructor
//public class SearchService {
//    @Autowired
//    private SearchRepository searchRepository;
//
//    @Autowired
//    private MemberRepository memberRepository; // 가정: Member 엔터티와 DTO 변환을 위해 필요
//
//    @Autowired
//    private EmotionRepository emotionRepository; // 가정: Emotion 엔터티와 DTO 변환을 위해 필요
//
//    public SearchDTO getSearchDTOById(int searchNo) {
//        Search search = searchRepository.findById(searchNo).orElse(null);
//        if (search == null) {
//            return null;
//        }
//
//        SearchDTO dto = new SearchDTO();
//        dto.setSearchNo(search.getSearchNo());
//        dto.setContentNo(search.getContentNo());
//
//        // MemberSimpleDTO 변환 로직
//        MemberSimpleDTO memberDTO = new MemberSimpleDTO();
//        // memberDTO의 필드 설정 로직 (e.g., memberDTO.setName(search.getMember().getName());)
//        dto.setMember(memberDTO);
//
//        // EmotionDTO 변환 로직
//        EmotionDTO emotionDTO = new EmotionDTO();
//        // emotionDTO의 필드 설정 로직 (e.g., emotionDTO.setType(search.getEmotion().getType());)
//        dto.setEmotion(emotionDTO);
//
//        return dto;
//    }
//}
