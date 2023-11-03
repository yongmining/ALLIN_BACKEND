
package com.allin.filmface.choiceContents.music.repository;


import com.allin.filmface.choiceContents.music.entity.GuestMusic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuestMusicRepository extends JpaRepository<GuestMusic, Integer> {
    List<GuestMusic> findByGuestMusicTitleAndGuestNo(String guestGuestMusicTitle, Integer guestNo);
}
