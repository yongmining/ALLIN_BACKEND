
package com.allin.filmface.choiceContents.youtube.repository;
import com.allin.filmface.choiceContents.youtube.entity.GuestYoutube;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuestYoutubeRepository extends JpaRepository<GuestYoutube, Integer> {
    // Emotion의 결과와 회원 번호에 따라 Youtube 데이터를 조회합니다.
    List<GuestYoutube> findByGuestEmotion_guestEmotionResultAndGuestNo(String guestEmotionResult, Integer guestNo);

    List<GuestYoutube> findByGuestYoutubeTitleAndGuestNo(String guestYoutubeTitle, Integer guestNo);
}
