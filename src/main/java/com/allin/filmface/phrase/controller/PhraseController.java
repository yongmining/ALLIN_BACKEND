package com.allin.filmface.phrase.controller;

import com.allin.filmface.common.ResponseDTO;
import com.allin.filmface.phrase.dto.PhraseDTO;
import com.allin.filmface.phrase.service.PhraseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/phrase")
public class PhraseController {

    @Autowired
    private PhraseService phraseService;

    @GetMapping
    public ResponseEntity<?> getRandomPhrase() {
        PhraseDTO phrase = phraseService.getRandomPhrase();
        return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK, "조회성공", phrase));
    }
}

