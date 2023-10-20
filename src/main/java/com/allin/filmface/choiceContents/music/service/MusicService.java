package com.allin.filmface.choiceContents.music.service;

import com.allin.filmface.choiceContents.auth.Auth;
import com.allin.filmface.choiceContents.music.repository.MusicRepository;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchResult;
import com.allin.filmface.choiceContents.music.entity.Music;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MusicService {

    @Autowired
    private MusicRepository musicRepository;

    @Autowired
    private Auth auth;
    private static YouTube youtube;

    public List<Music> getMusicContentsByEmotionResult(String emotionResult) {
        return getMusicContentsByQuery(emotionResult);
    }

    public List<Music> getMusicContentsByQuery(String query) {
        List<Music> resultMusics = new ArrayList<>();

        try {
            youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, request -> {})
                    .setApplicationName("YOUR_APP_NAME").build();
            YouTube.Search.List search = youtube.search().list("id,snippet");
            search.setKey(auth.getApiKey());
            search.setQ(query);
            search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
            search.setMaxResults(10L);

            List<SearchResult> searchResults = search.execute().getItems();

            for (SearchResult sr : searchResults) {
                Music music = new Music();
                String musicLink = "https://www.youtube.com/watch?v=" + sr.getId().getVideoId();
                music.setMusicLink(musicLink);
                music.setMusicTitle(sr.getSnippet().getTitle());
                musicRepository.save(music);
                resultMusics.add(music);
            }

        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error fetching YouTube videos for music", e);
        }
        return resultMusics;
    }
}
