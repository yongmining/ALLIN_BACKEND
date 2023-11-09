package com.allin.filmface.choiceContents.youtube.repository;

import com.allin.filmface.choiceContents.youtube.entity.GuestYoutube;
import com.allin.filmface.choiceContents.youtube.entity.Youtube;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface YoutubeRepository extends JpaRepository<Youtube, Integer> {
    Optional<Youtube> findByYoutubeLinkAndMemberNo(String youtubeLink, Integer memberNo);

    List<Youtube> findByEmotion_EmotionResultAndMemberNo(String emotionResult, Integer memberNo);

    List<Youtube> findByYoutubeTitleAndMemberNo(String youtubeTitle, Integer memberNo);

//    @Query("SELECT SUM(y.niceCount) FROM Youtube y WHERE y.youtubeLink = :youtubeLink")
//    Long findTotalNiceCountByYoutubeLink(@Param("youtubeLink") String youtubeLink);

    @Query("SELECT SUM(y.niceCount) FROM Youtube y WHERE y.youtubeLink = :youtubeLink")
    Integer findTotalNiceCountByYoutubeLink(@Param("youtubeLink") String youtubeLink);


    @Modifying
    @Query("UPDATE Youtube y SET y.niceCount = y.niceCount + 1 WHERE y.youtubeLink = :youtubeLink")
    void incrementNiceCountByLink(@Param("youtubeLink") String youtubeLink);

    @Modifying
    @Query("UPDATE Youtube y SET y.niceCount = y.niceCount - 1 WHERE y.youtubeLink = :youtubeLink")
    void decrementNiceCountByLink(@Param("youtubeLink") String youtubeLink);

//    @Modifying
//    @Query("UPDATE Youtube y SET y.niceCount = :count WHERE y.youtubeLink = :youtubeLink")
//    void updateNiceCountByLink(@Param("youtubeLink") String youtubeLink, @Param("count") Integer count);

    @Modifying
    @Query("UPDATE Youtube y SET y.niceCount = (SELECT COUNT(yn) FROM YoutubeNice yn WHERE yn.youtube.youtubeLink = y.youtubeLink) WHERE y.youtubeLink = :youtubeLink")
    void updateNiceCountByLink(@Param("youtubeLink") String youtubeLink);

    Optional<Youtube> findByYoutubeLink(String youtubeLink);
}

