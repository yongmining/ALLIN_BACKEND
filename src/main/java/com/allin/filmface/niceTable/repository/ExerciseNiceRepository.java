package com.allin.filmface.niceTable.repository;

import com.allin.filmface.niceTable.entity.ExerciseNice;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseNiceRepository extends JpaRepository<ExerciseNice, Integer> {

    List<ExerciseNice> findByMember_MemberNoAndExercise_ExerciseLink(Integer memberNo, String exerciseLink);

    Long countByExercise_ExerciseLink(String exerciseLink);

    @Query("SELECT e.exerciseNo, e.exerciseLink, e.exerciseTitle, e.thumbnailUrl, COUNT(e.exerciseLink) as niceCount, mem.memberNo, mem.memberGender, mem.memberAge " +
            "FROM Exercise e " +
            "JOIN e.emotion em " +
            "JOIN ExerciseNice en ON e.exerciseLink = en.exercise.exerciseLink " +
            "JOIN Member mem ON mem.memberNo = en.member.memberNo " +
            "WHERE em.emotionNo = (SELECT MAX(em2.emotionNo) FROM Emotion em2 WHERE em2.memberNo = :memberNo) " +
            "AND mem.memberAge BETWEEN :ageRangeStart AND :ageRangeEnd " +
            "GROUP BY e.exerciseNo, e.exerciseLink, e.exerciseTitle, e.thumbnailUrl, mem.memberNo, mem.memberGender, mem.memberAge " +
            "ORDER BY COUNT(e.exerciseLink) DESC")
    List<Object[]> findTop6ExerciseByEmotionAndAge(@Param("memberNo") int memberNo, @Param("ageRangeStart") int ageRangeStart, @Param("ageRangeEnd") int ageRangeEnd);
}
