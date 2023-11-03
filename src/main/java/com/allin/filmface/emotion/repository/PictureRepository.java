package com.allin.filmface.emotion.repository;

import com.allin.filmface.emotion.entity.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface PictureRepository extends JpaRepository<Picture, Long> {


    // 멤버 하나에 사진 여러 개 연결되도록 하기 위해
//    List<Picture> findByMemberNo(int member);
}
