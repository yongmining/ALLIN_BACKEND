
package com.allin.filmface.emotion.repository;

import com.allin.filmface.emotion.entity.Emotion;
import org.springframework.data.jpa.repository.JpaRepository;




public interface EmotionRepository extends JpaRepository<Emotion, Long> {

}

// package com.allin.filmface.emotion.repository;

// import com.allin.filmface.feedback.entity.Feedback;
// import org.springframework.data.jpa.repository.JpaRepository;

// import java.util.List;


// public interface EmotionRepository extends JpaRepository<Feedback, Long> {
//     List<Feedback> findByMember_MemberNo(int memberNo);
// }

