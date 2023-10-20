package com.allin.filmface.emotion.repository;

import com.allin.filmface.emotion.entity.Picture;
import org.springframework.data.jpa.repository.JpaRepository;




public interface PictureRepository extends JpaRepository<Picture, Long> {

}
