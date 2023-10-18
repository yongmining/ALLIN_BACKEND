package com.allin.filmface.phrase.repository;

import com.allin.filmface.phrase.entity.Phrase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhraseRepository extends JpaRepository<Phrase, Integer> {
}
