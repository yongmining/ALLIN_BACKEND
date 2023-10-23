package com.allin.filmface.choiceContents.music.repository;

import com.allin.filmface.choiceContents.music.entity.Music;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusicRepository extends JpaRepository<Music, Long> {
    // Emotion의 결과와 회원 번호에 따라 Music 데이터를 조회합니다.
    List<Music> findByEmotion_EmotionResultAndMemberNo(String emotionResult, Integer memberNo);
}