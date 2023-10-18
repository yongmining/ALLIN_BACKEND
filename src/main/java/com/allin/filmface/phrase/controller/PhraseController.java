package com.allin.filmface.phrase.controller;

import com.allin.filmface.phrase.dto.PhraseDTO;
import com.allin.filmface.phrase.service.PhraseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/phrase")
public class PhraseController {

    @Autowired
    private PhraseService phraseService;

    @GetMapping
    public PhraseDTO getRandomPhrase() {
        return phraseService.getRandomPhrase();
    }
}
