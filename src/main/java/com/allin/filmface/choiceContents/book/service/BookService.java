package com.allin.filmface.choiceContents.book.service;

import com.allin.filmface.choiceContents.book.dto.BookDTO;
import com.allin.filmface.choiceContents.book.entity.Book;
import com.allin.filmface.choiceContents.book.repository.BookRepository;
import com.allin.filmface.emotion.entity.Emotion;
import com.allin.filmface.emotion.repository.EmotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private EmotionRepository emotionRepository;

    @Autowired
    private BookRepository bookRepository;

    public List<BookDTO> findBooksByLatestEmotionOfMember(Integer memberNo) {
        Emotion latestEmotion = emotionRepository.findFirstByMemberNoOrderByEmotionNoDesc(memberNo);

        if (latestEmotion == null) {
            // 마지막 감정 데이터가 없는 경우
            return null;
        }

        return convertToDTO(bookRepository.findByEmotionResult(latestEmotion.getEmotionResult()));
    }

    private List<BookDTO> convertToDTO(List<Book> books) {
        return books.stream()
                .map(book -> {
                    BookDTO bookDTO = new BookDTO();
                    bookDTO.setBookNo(book.getBookNo());
                    bookDTO.setTitle(book.getBookTitle());
                    bookDTO.setSubTitle(book.getBookSubTitle());
                    bookDTO.setImageName(book.getBookImageName());
                    bookDTO.setAuthor(book.getBookAuthor());
                    bookDTO.setEmotionResult(book.getEmotionResult());

                    // URL 설정
                    bookDTO.setThumbnailUrl(book.getThumbnailUrl());

                    return bookDTO;
                })
                .collect(Collectors.toList());
    }

    // getBookImage 메서드는 더 이상 필요 없으므로 제거 가능
}