package com.allin.filmface.choiceContents.music.repository;

import com.allin.filmface.choiceContents.music.entity.Music;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MusicRepository extends JpaRepository<Music, Integer> {

    List<Music> findByMusicTitleAndMemberNo(String musicTitle, Integer memberNo);

    Optional<Music> findByMusicLinkAndMemberNo(String musicLink, Integer memberNo);


    @Modifying
    @Query("UPDATE Music m SET m.niceCount = m.niceCount + 1 WHERE m.musicLink = :musicLink")
    void incrementNiceCountByLink(@Param("musicLink") String musicLink);

    @Modifying
    @Query("UPDATE Music m SET m.niceCount = m.niceCount - 1 WHERE m.musicLink = :musicLink")
    void decrementNiceCountByLink(@Param("musicLink") String musicLink);

    @Modifying
    @Query("UPDATE Music m SET m.niceCount = ?2 WHERE m.musicLink = ?1")
    void updateNiceCountByLink(String musicLink, int niceCount);
    Optional<Music> findByMusicLink(String musicLink);

}