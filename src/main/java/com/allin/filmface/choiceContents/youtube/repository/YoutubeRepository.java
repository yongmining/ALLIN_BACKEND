package com.allin.filmface.choiceContents.youtube.repository;

import com.allin.filmface.choiceContents.youtube.entity.Youtube;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface YoutubeRepository extends JpaRepository<Youtube, Long> {
    // Emotion의 결과와 회원 번호에 따라 Youtube 데이터를 조회합니다.
    List<Youtube> findByEmotion_EmotionResultAndMemberNo(String emotionResult, Integer memberNo);

    // YoutubeNo에 해당하는 동영상의 nice_no 합계를 조회
    @Query("SELECT SUM(nice.niceNo) FROM YoutubeNice nice WHERE nice.youtubeNo.youtubeNo = :youtubeNo")
    Long findTotalNiceCountByYoutubeNo(@Param("youtubeNo") int youtubeNo);
}
