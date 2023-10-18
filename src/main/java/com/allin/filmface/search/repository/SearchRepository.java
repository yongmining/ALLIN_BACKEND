package com.allin.filmface.search.repository;

import com.allin.filmface.member.entity.Member;
import com.allin.filmface.search.dto.SearchDTO;
import com.allin.filmface.search.entity.Search;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface SearchRepository extends JpaRepository<Search, Integer> {

    Page<SearchDTO> findByEmotionEmotionNo(Pageable pageable,int emotionNo);

}
