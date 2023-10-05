package com.allin.filmface.search.repository;

import com.allin.filmface.member.entity.Member;
import com.allin.filmface.search.entity.SearchEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface  SearchRepository extends JpaRepository<SearchEntity, Integer> {

    Page<SearchEntity> findByMember(Pageable page, Optional<Member> memberNo);

}
