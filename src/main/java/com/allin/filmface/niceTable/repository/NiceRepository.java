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

}
