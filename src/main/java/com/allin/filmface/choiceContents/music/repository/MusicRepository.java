package com.allin.filmface.choiceContents.music.repository;

import com.allin.filmface.choiceContents.music.entity.Music;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusicRepository extends JpaRepository<Music, Long> {
    List<Music> findByEmotion_EmotionResult(String emotionResult);
}
