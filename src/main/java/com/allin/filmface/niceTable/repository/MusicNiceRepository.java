package com.allin.filmface.niceTable.repository;

import com.allin.filmface.niceTable.entity.MusicNice;
import com.allin.filmface.niceTable.entity.YoutubeNice;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusicNiceRepository extends JpaRepository<MusicNice, Integer> {

    List<MusicNice> findByMember_MemberNoAndMusic_MusicLink(Integer memberNo, String musicLink);

    Long countByMusic_MusicLink(String musicLink);

    @Query("SELECT m.musicNo, m.musicLink, m.musicTitle, m.thumbnailUrl, COUNT(m.musicLink) as niceCount, mem.memberNo, mem.memberGender, mem.memberAge " +
            "FROM Music m " +
            "JOIN m.emotion e " +
            "JOIN MusicNice mn ON m.musicLink = mn.music.musicLink " +
            "JOIN Member mem ON mem.memberNo = mn.member.memberNo " +
            "WHERE e.emotionNo = (SELECT MAX(e2.emotionNo) FROM Emotion e2 WHERE e2.memberNo = :memberNo) " +
            "AND mem.memberAge BETWEEN :ageRangeStart AND :ageRangeEnd " +
            "GROUP BY m.musicNo, m.musicLink, m.musicTitle, m.thumbnailUrl, mem.memberNo, mem.memberGender, mem.memberAge " +
            "ORDER BY COUNT(m.musicLink) DESC")
    List<Object[]> findTop6MusicByEmotionAndAge(@Param("memberNo") int memberNo, @Param("ageRangeStart") int ageRangeStart, @Param("ageRangeEnd") int ageRangeEnd);


}
