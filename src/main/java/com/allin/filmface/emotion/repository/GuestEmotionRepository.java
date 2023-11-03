
package com.allin.filmface.emotion.repository;
import com.allin.filmface.emotion.entity.GuestEmotion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuestEmotionRepository extends JpaRepository<GuestEmotion, Long> {

    GuestEmotion findFirstByGuestNoOrderByGuestEmotionNoDesc(Integer guestNo);


    List<GuestEmotion> findByGuestNo(Integer guestNo);
}



// public interface EmotionRepository extends JpaRepository<Feedback, Long> {
//     List<Feedback> findByMember_MemberNo(int memberNo);
// }

