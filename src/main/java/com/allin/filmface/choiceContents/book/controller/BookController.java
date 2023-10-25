package com.allin.filmface.choiceContents.book.controller;

import com.allin.filmface.choiceContents.book.dto.BookDTO;
import com.allin.filmface.choiceContents.book.service.BookService;
import com.allin.filmface.choiceContents.book.entity.Book;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1/book")
@AllArgsConstructor
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/emotion")
    public ResponseEntity<List<BookDTO>> getBooksByEmotion(@RequestParam Integer memberNo) {
        try {
            List<BookDTO> books = bookService.findBooksByLatestEmotionOfMember(memberNo);

            // 이미지 데이터를 가져와서 BookDTO에 추가
            for (BookDTO book : books) {
                byte[] imageData = bookService.getBookImage(book.getBookNo());
                book.setImageData(imageData);
            }

            return new ResponseEntity<>(books, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
