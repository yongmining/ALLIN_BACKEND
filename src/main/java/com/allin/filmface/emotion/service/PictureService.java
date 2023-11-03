package com.allin.filmface.emotion.service;

import com.allin.filmface.emotion.dto.PictureDTO;
import com.allin.filmface.emotion.entity.Picture;
import com.allin.filmface.emotion.repository.PictureRepository;
import com.allin.filmface.member.dto.MemberDTO;
import com.allin.filmface.member.entity.Member;
import com.allin.filmface.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PictureService {

    @Autowired
    private final PictureRepository pictureRepository;
    private final MemberRepository memberRepository;

    public Picture findById(Long id) {
        return pictureRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Picture not found."));
    }

    public Picture save(PictureDTO pictureDTO, byte[] file, MemberDTO memberDTO) throws IOException {
        // Create a new Picture entity and set its properties
        Picture picture = new Picture();
        picture.setImageName(pictureDTO.getImageName());
        picture.setImage(file); // Set the image data from the byte array

        // Find the Member entity by using the memberDTO information
        Member member = memberRepository.findBySocialId(memberDTO.getSocialLogin(), memberDTO.getSocialId());

        // Set the Member entity in the Picture entity
        picture.setMember(member);
        System.out.println("picture.getMember() = " + picture.getMember());
        // Save the picture entity in the repository
        return pictureRepository.save(picture);
    }
}