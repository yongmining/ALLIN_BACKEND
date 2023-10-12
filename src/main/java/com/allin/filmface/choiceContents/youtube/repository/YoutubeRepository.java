package com.allin.filmface.choiceContents.youtube.repository;

import com.allin.filmface.choiceContents.youtube.entity.Youtube;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface YoutubeRepository extends JpaRepository<Youtube, Long> {

    List<Youtube> findBySearchEmotionEmotionResult(String emotionResult);


}
