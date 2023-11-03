package com.allin.filmface.choiceContents.music.service;

import com.allin.filmface.choiceContents.auth.Auth;
import com.allin.filmface.choiceContents.music.entity.Music;
import com.allin.filmface.choiceContents.music.repository.MusicRepository;
import com.allin.filmface.emotion.entity.Emotion;
import com.allin.filmface.emotion.repository.EmotionRepository;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MusicService {

    @Autowired
    private MusicRepository musicRepository;

    @Autowired
    private EmotionRepository emotionRepository;

    @Autowired
    private Auth auth;

    private static YouTube youtube;


    @Transactional
    public void incrementMusicNiceCountByLink(String musicLink) {
        Optional<Music> musicOptional = musicRepository.findByMusicLink(musicLink);
        if (musicOptional.isPresent()) {
            Music music = musicOptional.get();
            music.setNiceCount(music.getNiceCount() + 1);
            musicRepository.save(music);
        } else {
            throw new RuntimeException("Music not found");
        }
    }

    @Transactional
    public void decrementMusicNiceCountByLink(String musicLink) {
        Optional<Music> musicOptional = musicRepository.findByMusicLink(musicLink);
        if (musicOptional.isPresent()) {
            Music music = musicOptional.get();
            music.setNiceCount(music.getNiceCount() - 1);
            musicRepository.save(music);
        } else {
            throw new RuntimeException("Music not found");
        }
    }
    public List<Music> getMusicContentsByQuery(String query, Integer memberNo) {
        List<Music> resultMusics = new ArrayList<>();

        try {
            youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, request -> {})
                    .setApplicationName("YOUR_APP_NAME").build();
            YouTube.Search.List search = youtube.search().list("id,snippet");
            search.setKey(auth.getApiKey());
            search.setQ(query);
            search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
            search.setMaxResults(10L);
            search.setType("video");
            search.setVideoCategoryId("10");

            List<SearchResult> searchResults = search.execute().getItems();

            Emotion relatedEmotion = emotionRepository.findFirstByMemberNoOrderByEmotionNoDesc(memberNo);

            for (SearchResult sr : searchResults) {
                String musicTitle = sr.getSnippet().getTitle();

                // Check if music with the same title already exists in the database for the user.
                List<Music> existingMusics = musicRepository.findByMusicTitleAndMemberNo(musicTitle, memberNo);

                if (existingMusics.isEmpty()) {
                    Music music = new Music();
                    if (relatedEmotion != null) {
                        music.setEmotion(relatedEmotion);
                        music.setMemberNo(memberNo);
                    }
                    String youtubeLink = "https://www.youtube.com/watch?v=" + sr.getId().getVideoId();
                    music.setMusicLink(youtubeLink);
                    music.setMusicTitle(musicTitle);
                    music.setThumbnailUrl(sr.getSnippet().getThumbnails().getDefault().getUrl());
                    musicRepository.save(music);
                    resultMusics.add(music);
                } else {
                    // 이미 DB에 존재하는 음악도 결과 리스트에 추가
                    resultMusics.addAll(existingMusics);
                }
            }

        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error fetching YouTube musics", e);
        }

        return resultMusics;
    }
}
