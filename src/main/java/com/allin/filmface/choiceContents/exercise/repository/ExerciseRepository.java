package com.allin.filmface.choiceContents.exercise.repository;

import com.allin.filmface.choiceContents.exercise.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    List<Exercise> findByExerciseTitleAndMemberNo(String exerciseTitle, Integer memberNo);

}
