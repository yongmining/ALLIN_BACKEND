package com.allin.filmface.choiceContents.youtube.repository;

import com.allin.filmface.choiceContents.youtube.entity.Youtube;
import org.springframework.data.jpa.repository.JpaRepository;

public interface YoutubeRepository extends JpaRepository<Youtube, Integer> {
}
