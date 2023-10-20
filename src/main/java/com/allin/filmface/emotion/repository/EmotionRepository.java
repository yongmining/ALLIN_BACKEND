//package com.allin.filmface.emotion.repository;
//
//import com.allin.filmface.emotion.entity.Emotion;
//import com.allin.filmface.feedback.entity.Feedback;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//
//public interface EmotionRepository extends JpaRepository<Emotion, Long> {
//    Emotion findByEmotionResultAndMemberNoOrderByCreatedAtDesc(String emotionResult, Integer memberNo);
//
//
//}
