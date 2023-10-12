//package com.allin.filmface.search.service;
//
//import com.allin.filmface.member.repository.MemberRepository;
//import com.allin.filmface.search.dto.SearchDTO;
//import com.allin.filmface.search.entity.Search;
//import com.allin.filmface.search.repository.SearchRepository;
//import lombok.AllArgsConstructor;
//import org.modelmapper.ModelMapper;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.stereotype.Service;
//
//@Service
//@AllArgsConstructor
//public class SearchService {
//
//    private final SearchRepository searchRepository;
//    private final MemberRepository memberRepository;
//    private final ModelMapper modelMapper;
//
//
//    public Page<SearchDTO> findAllSearch(Pageable page, int memberNo) {
//        page = PageRequest.of(page.getPageNumber() <= 0 ? 0 : page.getPageNumber() -1, page.getPageSize(),
//                Sort.by("searchNo"));
//
//        Page<Search> searchs = searchRepository.findByMember(page, memberRepository.findById(memberNo));
//
//        return searchs.map(search -> modelMapper.map(search, SearchDTO.class));
//    }
//}
