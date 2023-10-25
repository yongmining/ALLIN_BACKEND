package com.allin.filmface.choiceContents.book.repository;

import com.allin.filmface.choiceContents.book.entity.Book;
import com.allin.filmface.emotion.entity.Emotion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByEmotionResult(String emotionResult);

}