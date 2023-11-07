package com.allin.filmface.emotion.service;

import com.allin.filmface.emotion.dto.EmotionDTO;
import com.allin.filmface.emotion.dto.PictureDTO;
import com.allin.filmface.emotion.entity.Emotion;
import com.allin.filmface.emotion.entity.Picture;
import com.allin.filmface.emotion.repository.PictureRepository;
import com.allin.filmface.emotion.repository.EmotionRepository;
import com.allin.filmface.member.dto.MemberDTO;
import com.allin.filmface.member.entity.Member;
import com.allin.filmface.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;

//@Service
//@RequiredArgsConstructor
//public class PictureService {
//
//    @Autowired
//    private final PictureRepository pictureRepository;
//    private final MemberRepository memberRepository;
//
//    public Picture findById(Long id) {
//        return pictureRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Picture not found."));
//    }
//
//    public Picture save(PictureDTO pictureDTO, byte[] file, MemberDTO memberDTO) throws IOException {
//        // Create a new Picture entity and set its properties
//        Picture picture = new Picture();
//        picture.setImageName(pictureDTO.getImageName());
//        picture.setImage(file); // Set the image data from the byte array
//
//        // Find the Member entity by using the memberDTO information
//        Member member = memberRepository.findBySocialId(memberDTO.getSocialLogin(), memberDTO.getSocialId());
//
//        // Set the Member entity in the Picture entity
//        picture.setMember(member);
//        System.out.println("picture.getMember() = " + picture.getMember());
//        // Save the picture entity in the repository
//        return pictureRepository.save(picture);
//    }
//
//    public void savePictureWithEmotion(EmotionDTO emotionDTOWithResults, PictureDTO pictureDTO) {
//    }
//}

@Service
public class PictureService {

    private final PictureRepository pictureRepository;
    private final EmotionRepository emotionRepository;

    public PictureService(PictureRepository pictureRepository, EmotionRepository emotionRepository) {
        this.pictureRepository = pictureRepository;
        this.emotionRepository = emotionRepository;
    }

    public Picture findById(Long id) {
        return pictureRepository.findById(Math.toIntExact(id)).orElseThrow(() -> new NoSuchElementException("Picture not found."));
    }

    public void savePictureWithEmotion(EmotionDTO emotionDTO, PictureDTO pictureDTO) {
        // PictureDTO를 Picture 엔티티로 변환
        Picture picture = new Picture();
        picture.setImage(pictureDTO.getImage());
        picture.setImageName(pictureDTO.getImageName());

        // 데이터베이스에 이미지 저장
        pictureRepository.save(picture);

        // EmotionDTO를 Emotion 엔티티로 변환
        Emotion emotion = new Emotion();
        emotion.setEmotionResult(emotionDTO.getEmotionResult());
        emotion.setEmotionAge(emotionDTO.getEmotionAge());
        emotion.setEmotionGender(emotionDTO.getEmotionGender());

        // Picture와 Emotion 관계 설정 (Emotion이 Picture를 참조)
        emotion.setPicture(picture);

        // 데이터베이스에 감정 정보 저장
        emotionRepository.save(emotion);
    }

    public void savePictureWithEmotion(Picture picture) {
    }
}