package com.allin.filmface.emotion.repository;

import com.allin.filmface.emotion.entity.Emotion;
import com.allin.filmface.emotion.entity.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface EmotionRepository extends JpaRepository<Emotion, Long> {
    Emotion findFirstByMemberNoOrderByEmotionNoDesc(Integer memberNo);
    //List<Picture> findByMember_MemberNo(int memberNo);

    List<Emotion> findByEmotionResult(String emotionResult);

    // 다른 필요한 커스텀 쿼리 메소드 추가 가능

}


// public interface EmotionRepository extends JpaRepository<Feedback, Long> {
//     List<Feedback> findByMember_MemberNo(int memberNo);
// }

