package com.allin.filmface.phrase.service;

import com.allin.filmface.phrase.dto.EmotionPhraseDTO;
import com.allin.filmface.phrase.entity.EmotionPhrase;
import com.allin.filmface.phrase.repository.EmotionPhraseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmotionPhraseService {

    @Autowired
    private EmotionPhraseRepository emotionPhraseRepository;

    public EmotionPhraseDTO getRandomEmotionPhrase() {
        long count = emotionPhraseRepository.count();

        int randomId = (int) (Math.random() * count) + 1;
        EmotionPhrase emotionPhrase = emotionPhraseRepository.findById(randomId).orElseThrow(() -> new IllegalArgumentException("EmotionPhrase not found"));

        return  new EmotionPhraseDTO(emotionPhrase.getEmotionPhraseNo(), emotionPhrase.getEmotionPhraseContent());
    }
}
