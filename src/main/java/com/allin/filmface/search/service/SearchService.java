package com.allin.filmface.search.service;

import com.allin.filmface.emotion.dto.EmotionDTO;
import com.allin.filmface.emotion.entity.Emotion;
import com.allin.filmface.member.dto.MemberSimpleDTO;
import com.allin.filmface.member.repository.MemberRepository;
import com.allin.filmface.search.dto.SearchDTO;
import com.allin.filmface.search.entity.Search;
import com.allin.filmface.search.repository.SearchRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class SearchService {

    private final SearchRepository searchRepository;

    @Autowired
    public SearchService(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

    public SearchDTO getSearchDTOById(int searchNo) {
        Search search = searchRepository.findById(searchNo).orElse(null);
        if (search == null) {
            return null;
        }

        SearchDTO dto = new SearchDTO();
        dto.setSearchNo(search.getSearchNo());
        dto.setContentNo(search.getContentNo());

        return dto;
    }

    public Page<SearchDTO> findAllSearchBasedOnEmotion(Pageable pageable, int emotionNo) {
        Page<Search> searches = searchRepository.findAllByEmotion_EmotionNo(emotionNo, pageable);
        Page<SearchDTO> searchDTOs = searches.map(this::convertToDTO);

        return searchDTOs;
    }

    private SearchDTO convertToDTO(Search search) {
        SearchDTO dto = new SearchDTO();
        dto.setSearchNo(search.getSearchNo());
        dto.setContentNo(search.getContentNo());

        return dto;

    }
}