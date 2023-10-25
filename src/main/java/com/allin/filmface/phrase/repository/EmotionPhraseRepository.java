package com.allin.filmface.phrase.repository;

import com.allin.filmface.phrase.entity.EmotionPhrase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface EmotionPhraseRepository  extends JpaRepository<EmotionPhrase, Integer> {
}
