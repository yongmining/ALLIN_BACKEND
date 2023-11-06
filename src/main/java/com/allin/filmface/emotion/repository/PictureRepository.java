package com.allin.filmface.emotion.repository;

import com.allin.filmface.emotion.entity.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;



public interface PictureRepository extends JpaRepository<Picture, Integer> {

    List<Picture> findByMember_MemberNo(int memberNo);

    // 다른 필요한 커스텀 쿼리 메소드 추가 가능
}
