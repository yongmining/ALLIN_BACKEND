package com.allin.filmface.choiceContents.book.service;

import com.allin.filmface.choiceContents.book.dto.BookDTO;
import com.allin.filmface.choiceContents.book.dto.GuestBookDTO;
import com.allin.filmface.choiceContents.book.entity.Book;
import com.allin.filmface.choiceContents.book.entity.GuestBook;
import com.allin.filmface.choiceContents.book.repository.BookRepository;
import com.allin.filmface.choiceContents.book.repository.GuestBookRepository;
import com.allin.filmface.emotion.entity.Emotion;
import com.allin.filmface.emotion.entity.GuestEmotion;
import com.allin.filmface.emotion.repository.EmotionRepository;
import com.allin.filmface.emotion.repository.GuestEmotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GuestBookService {

    @Autowired
    private GuestEmotionRepository guestEmotionRepository;

    @Autowired
    private GuestBookRepository guestBookRepository;

    public List<GuestBookDTO> findGuestBooksByLatestGuestEmotionOfGuest(Integer GuestNo) {
        GuestEmotion latestEmotion = guestEmotionRepository.findFirstByGuestNoOrderByGuestEmotionNoDesc(GuestNo);

        if (latestEmotion == null) {
            // 마지막 감정 데이터가 없는 경우
            return Collections.emptyList(); // 빈 리스트 반환
        }



        return convertToDTO(guestBookRepository.findByGuestEmotionResult(latestEmotion.getGuestEmotionResult()));
    }

    private List<GuestBookDTO> convertToDTO(List<GuestBook> books) {
        return books.stream()
                .map(book -> {
                    GuestBookDTO guestBookDTO = new GuestBookDTO();
                    guestBookDTO.setGuestBookNo(book.getGuestBookNo());
                    guestBookDTO.setTitle(book.getBookTitle());
                    guestBookDTO.setSubTitle(book.getBookSubTitle());
                    guestBookDTO.setImageName(book.getBookImageName());
                    guestBookDTO.setAuthor(book.getBookAuthor());
                    guestBookDTO.setGuestEmotionResult(book.getGuestEmotionResult());

                    // URL 설정
                    guestBookDTO.setThumbnailUrl(book.getThumbnailUrl());

                    return guestBookDTO;
                })
                .collect(Collectors.toList());
    }

    // getBookImage 메서드는 더 이상 필요 없으므로 제거 가능
}