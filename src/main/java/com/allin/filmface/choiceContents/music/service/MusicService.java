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

import java.util.ArrayList;
import java.util.List;

@Service
public class MusicService {

    @Autowired
    private MusicRepository musicRepository;

    @Autowired
    private EmotionRepository emotionRepository;

    @Autowired
    private Auth auth;  // API Key 등을 제공하는 클래스

    private static YouTube youtube;

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
            search.setType("video"); // 영상만 검색
            search.setVideoCategoryId("10"); // 음악 카테고리

            List<SearchResult> searchResults = search.execute().getItems();

            Emotion relatedEmotion = emotionRepository.findFirstByMemberNoOrderByEmotionNoDesc(memberNo);

            for (SearchResult sr : searchResults) {
                Music music = new Music();
                if (relatedEmotion != null) {
                    music.setEmotion(relatedEmotion);
                    music.setMemberNo(memberNo);
                }
                String youtubeLink = "https://www.youtube.com/watch?v=" + sr.getId().getVideoId();
                music.setMusicLink(youtubeLink);
                music.setMusicTitle(sr.getSnippet().getTitle());
                music.setThumbnailUrl(sr.getSnippet().getThumbnails().getDefault().getUrl());
                musicRepository.save(music);
                resultMusics.add(music);
            }

        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error fetching YouTube musics", e);
        }

        return resultMusics;
    }
}
