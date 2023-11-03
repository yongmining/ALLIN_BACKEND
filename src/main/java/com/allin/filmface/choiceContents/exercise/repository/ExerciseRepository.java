package com.allin.filmface.choiceContents.exercise.repository;

import com.allin.filmface.choiceContents.exercise.entity.Exercise;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    List<Exercise> findByExerciseTitleAndMemberNo(String exerciseTitle, Integer memberNo);

    Optional<Exercise> findByExerciseLinkAndMemberNo(String exerciseLink, Integer memberNo);

    @Modifying
    @Query("UPDATE Exercise e SET e.niceCount = e.niceCount + 1 WHERE e.exerciseLink = :exerciseLink")
    void incrementNiceCountByLink(@Param("exerciseLink") String exerciseLink);

    @Modifying
    @Query("UPDATE Exercise e SET e.niceCount = e.niceCount - 1 WHERE e.exerciseLink = :exerciseLink")
    void decrementNiceCountByLink(@Param("exerciseLink") String exerciseLink);

    @Modifying
    @Query("UPDATE Exercise e SET e.niceCount = :count WHERE e.exerciseLink = :exerciseLink")
    void updateNiceCountByLink(@Param("exerciseLink") String exerciseLink, @Param("count") Integer count);

    Optional<Exercise> findByExerciseLink(String exerciseLink);
}
