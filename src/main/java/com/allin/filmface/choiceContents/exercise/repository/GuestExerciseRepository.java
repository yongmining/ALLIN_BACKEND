package com.allin.filmface.choiceContents.exercise.repository;

import com.allin.filmface.choiceContents.exercise.entity.GuestExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuestExerciseRepository extends JpaRepository<GuestExercise, Integer> {
    List<GuestExercise> findByGuestExerciseTitleAndGuestNo(String guestExerciseTitle, Integer guestNo);
}
