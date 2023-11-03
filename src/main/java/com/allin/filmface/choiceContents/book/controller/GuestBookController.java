package com.allin.filmface.choiceContents.book.controller;


import com.allin.filmface.choiceContents.book.dto.GuestBookDTO;
import com.allin.filmface.choiceContents.book.service.GuestBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/book")
public class GuestBookController {

    @Autowired
    private GuestBookService guestBookService;

    @GetMapping("/guest/emotion")
    public ResponseEntity<?> getGuestBooksByEmotion(@RequestParam Integer guestNo) {
        try {
            List<GuestBookDTO> books = guestBookService.findGuestBooksByLatestGuestEmotionOfGuest(guestNo);
            return ResponseEntity.ok(books);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("조회 실패");
        }
    }
}
