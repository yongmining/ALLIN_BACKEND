package com.allin.filmface.phrase.service;

import com.allin.filmface.phrase.dto.PhraseDTO;
import com.allin.filmface.phrase.entity.Phrase;
import com.allin.filmface.phrase.repository.PhraseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhraseService {

    @Autowired
    private PhraseRepository phraseRepository;

    public PhraseDTO getRandomPhrase() {
        long count = phraseRepository.count();

        int randomId = (int) (Math.random() * count) + 1;
        Phrase phrase = phraseRepository.findById(randomId).orElseThrow(() -> new IllegalArgumentException("Phrase not found"));

        return new PhraseDTO(phrase.getPhraseNo(), phrase.getPhraseContent());
    }
}
