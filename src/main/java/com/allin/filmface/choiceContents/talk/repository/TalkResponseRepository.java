package com.allin.filmface.choiceContents.talk.repository;

import com.allin.filmface.choiceContents.talk.entity.TalkResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TalkResponseRepository extends JpaRepository<TalkResponse, Long> {
}
