package com.allin.filmface.niceTable.repository;

import com.allin.filmface.niceTable.entity.Nice;
import com.allin.filmface.niceTable.entity.YoutubeNice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NiceRepository extends JpaRepository<YoutubeNice, Integer> {
    List<YoutubeNice> findByMember_MemberNoAndYoutube_YoutubeNo(int memberNo, int youtubeNo);
    Long countByYoutube_YoutubeNo(int youtubeNo);

}
