package com.allin.filmface.choiceContents.book.service;

import com.allin.filmface.choiceContents.book.dto.BookDTO;
import com.allin.filmface.choiceContents.book.entity.Book;
import com.allin.filmface.choiceContents.book.repository.BookRepository;
import com.allin.filmface.emotion.entity.Emotion;
import com.allin.filmface.emotion.repository.EmotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
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

    public byte[] getBookImage(int bookNo) {
        try {
            // 책 번호를 기반으로 이미지 파일의 경로를 구성
            String imagePath = "api/v1/images/books/book" + bookNo + ".jpg";

            // 이미지 파일을 읽어서 byte 배열로 반환
            return readImage(imagePath);
        } catch (Exception e) {
            // 이미지를 찾을 수 없는 경우 빈 배열 반환 또는 예외 처리
            return new byte[0];
        }
    }

    private byte[] readImage(String imagePath) throws IOException {
        // 이미지 파일을 읽어서 byte 배열로 반환하는 코드를 작성해야 합니다.
        File imageFile = new File(imagePath);
        return Files.readAllBytes(imageFile.toPath());
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
                    bookDTO.setImageUrl("api/v1/images/books/" + book.getBookImageName());



                    // 이미지 데이터 설정 (이미지 번호로부터 읽어옴)
                    int bookNo = book.getBookNo();
                    byte[] imageData = getBookImage(bookNo);
                    bookDTO.setImageData(imageData);

                    return bookDTO;
                })
                .collect(Collectors.toList());
    }
}