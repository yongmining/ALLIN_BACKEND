package com.allin.filmface.emotion.repository;

import com.allin.filmface.emotion.entity.Emotion;
import org.springframework.data.jpa.repository.JpaRepository;




public interface EmotionRepository extends JpaRepository<Emotion, Long> {

}