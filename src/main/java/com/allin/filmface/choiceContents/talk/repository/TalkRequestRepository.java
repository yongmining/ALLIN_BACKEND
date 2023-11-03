package com.allin.filmface.choiceContents.talk.repository;

import com.allin.filmface.choiceContents.talk.entity.TalkRequest;
import com.allin.filmface.choiceContents.talk.entity.TalkResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface TalkRequestRepository extends JpaRepository<TalkRequest, Long> {
    @Query("SELECT tr FROM TalkRequest tr LEFT JOIN FETCH tr.talkResponse WHERE tr.userNo = :userNo")
    List<TalkRequest> findTalkRequestsWithResponseByUserNo(int userNo);

    @Query("SELECT COUNT(tr) FROM TalkRequest tr WHERE tr.userNo = :userNo")
    int getChatRequestCountByUserNo(int userNo);

}