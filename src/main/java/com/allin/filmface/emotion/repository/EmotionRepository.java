package com.allin.filmface.emotion.repository;

import com.allin.filmface.emotion.entity.Emotion;
import com.allin.filmface.emotion.entity.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface EmotionRepository extends JpaRepository<Emotion, Long> {
    Emotion findFirstByMemberNoOrderByEmotionNoDesc(Integer memberNo);
    List<Emotion> findByMemberNo(Integer memberNo);
  
    //List<Picture> findByMember_MemberNo(int memberNo);

    List<Emotion> findByEmotionResult(String emotionResult);

}


// public interface EmotionRepository extends JpaRepository<Feedback, Long> {
//     List<Feedback> findByMember_MemberNo(int memberNo);
// }

