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
    List<YoutubeNice> findByMember_MemberNoAndYoutube_YoutubeLink(int memberNo, String youtubeLink);
    Long countByYoutube_YoutubeLink(String youtubeLink);



@Query("SELECT y.youtubeNo, y.youtubeLink, y.youtubeTitle, y.thumbnailUrl, COUNT(y.youtubeLink) as niceCount, m.memberNo, m.memberGender, m.memberAge FROM Youtube y " +
        "JOIN y.emotion e " +
        "JOIN YoutubeNice yn ON y.youtubeLink = yn.youtube.youtubeLink " +
        "JOIN Member m ON m.memberNo = yn.member.memberNo " +
        "WHERE e.emotionNo = (SELECT MAX(e2.emotionNo) FROM Emotion e2 WHERE e2.memberNo = :memberNo) " +
        "AND m.memberAge BETWEEN :ageRangeStart AND :ageRangeEnd " +
        "GROUP BY y.youtubeNo, y.youtubeLink, y.youtubeTitle, y.thumbnailUrl, m.memberNo, m.memberGender, m.memberAge " +
        "ORDER BY COUNT(y.youtubeLink) DESC")
    List<Object[]> findTop6YoutubeByEmotionAndAge(@Param("memberNo") int memberNo, @Param("ageRangeStart") int ageRangeStart, @Param("ageRangeEnd") int ageRangeEnd);

}
