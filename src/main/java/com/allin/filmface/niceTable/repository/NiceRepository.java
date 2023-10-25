package com.allin.filmface.niceTable.repository;

import com.allin.filmface.choiceContents.youtube.entity.Youtube;
import com.allin.filmface.niceTable.entity.Nice;
import com.allin.filmface.niceTable.entity.YoutubeNice;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NiceRepository extends JpaRepository<YoutubeNice, Integer> {
    List<YoutubeNice> findByMember_MemberNoAndYoutubeNo_YoutubeNo(int memberNo, int youtubeNo);
    Long countByYoutubeNo_YoutubeNo(int youtubeNo);
    @Query("SELECT y FROM Youtube y JOIN Member m ON y.memberNo = m.memberNo WHERE CASE WHEN m.memberAge < 20 THEN 'teen' WHEN m.memberAge < 30 THEN 'twenties' WHEN m.memberAge < 40 THEN 'thirties' ELSE 'older' END = :ageGroup GROUP BY y.youtubeNo ORDER BY COUNT(y.youtubeNo) DESC")
    List<Youtube> findTopVideosByAgeGroup(@Param("ageGroup") String ageGroup);

//    @Query("SELECT y FROM Youtube y " +
//            "JOIN y.emotion e " +
//            "JOIN YoutubeNice yn ON y.youtubeNo = yn.youtubeNo.youtubeNo " +
//            "JOIN Member m ON m.memberNo = yn.member.memberNo " +
//            "WHERE e.emotionNo = (SELECT MAX(e2.emotionNo) FROM Emotion e2 WHERE e2.memberNo = :memberNo) " +
//            "AND m.memberNo = :memberNo " +
//            "AND m.memberAge BETWEEN :ageRangeStart AND :ageRangeEnd " +
//            "GROUP BY y " +
//            "HAVING COUNT(y.youtubeNo) > 0 " +
//            "ORDER BY COUNT(y) DESC")
//    List<Youtube> findTop6YoutubeByEmotionAndAge(@Param("memberNo") int memberNo, @Param("ageRangeStart") int ageRangeStart, @Param("ageRangeEnd") int ageRangeEnd);

    @Query("SELECT y FROM Youtube y " +
            "JOIN YoutubeNice yn ON y.youtubeNo = yn.youtubeNo.youtubeNo " +
            "JOIN Member m ON m.memberNo = yn.member.memberNo " +
            "WHERE m.memberNo = :memberNo " +
            "AND m.memberAge BETWEEN :ageRangeStart AND :ageRangeEnd " +
            "GROUP BY y.youtubeNo " +
            "HAVING COUNT(y.youtubeNo) = (SELECT MAX(e2.emotionNo) FROM Emotion e2 WHERE e2.memberNo = :memberNo) " +
            "ORDER BY COUNT(y.youtubeNo) DESC")
    List<Youtube> findTop6YoutubeByEmotionAndAge(@Param("memberNo") int memberNo, @Param("ageRangeStart") int ageRangeStart, @Param("ageRangeEnd") int ageRangeEnd);
}
