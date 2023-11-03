package com.allin.filmface.choiceContents.book.repository;

import com.allin.filmface.choiceContents.book.entity.GuestBook;
import com.allin.filmface.emotion.entity.GuestEmotion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuestBookRepository extends JpaRepository<GuestBook, Integer> {
    List<GuestBook> findByGuestEmotionResult(String guestEmotionResult);

}
