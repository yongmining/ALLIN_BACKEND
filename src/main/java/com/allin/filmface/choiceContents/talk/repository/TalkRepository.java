package com.allin.filmface.choiceContents.talk.repository;

import com.allin.filmface.choiceContents.talk.entity.Talk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TalkRepository extends JpaRepository<Talk, Integer> {
}
