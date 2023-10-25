package com.allin.filmface.choiceContents.talk.controller;

import com.allin.filmface.choiceContents.talk.dto.TalkDTO;
import com.allin.filmface.choiceContents.talk.service.TalkService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "소통 관련 API")
@RestController
@RequestMapping("/api/v1/talk")
@AllArgsConstructor
public class TalkController {

    private final TalkService talkService;

    @PostMapping("/add")
    public ResponseEntity<TalkDTO> addTalk(@RequestBody TalkDTO talkDTO) {
        TalkDTO addedTalk = talkService.addTalk(talkDTO);
        return ResponseEntity.ok(addedTalk);
    }

    @GetMapping("/all")
    public ResponseEntity<List<TalkDTO>> getAllTalks() {
        List<TalkDTO> allTalks = talkService.getAllTalks();
        return ResponseEntity.ok(allTalks);
    }

}
