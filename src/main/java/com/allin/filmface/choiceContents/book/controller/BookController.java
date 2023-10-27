package com.allin.filmface.choiceContents.book.controller;

import com.allin.filmface.choiceContents.book.dto.BookDTO;
import com.allin.filmface.choiceContents.book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/emotion")
    public ResponseEntity<?> getBooksByEmotion(@RequestParam Integer memberNo) {
        try {
            List<BookDTO> books = bookService.findBooksByLatestEmotionOfMember(memberNo);
            return ResponseEntity.ok(books);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("조회 실패");
        }
    }
}
