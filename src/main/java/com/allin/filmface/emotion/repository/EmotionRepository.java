package com.allin.filmface.emotion.repository;

import com.allin.filmface.emotion.entity.Emotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmotionRepository extends JpaRepository<Emotion, Long> {

    Emotion findFirstByMemberNoOrderByEmotionNoDesc(Integer memberNo);


    List<Emotion> findByMemberNo(Integer memberNo);
}
