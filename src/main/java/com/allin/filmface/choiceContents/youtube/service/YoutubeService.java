package com.allin.filmface.choiceContents.youtube.service;
import com.allin.filmface.choiceContents.auth.Auth;
import com.allin.filmface.emotion.entity.Emotion;
import com.allin.filmface.emotion.repository.EmotionRepository;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchResult;
import com.allin.filmface.choiceContents.youtube.entity.Youtube;
import com.allin.filmface.choiceContents.youtube.repository.YoutubeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.ArrayList;
import java.util.List;

@Service
public class YoutubeService {

    @Autowired
    private YoutubeRepository youtubeRepository;
    @Autowired
    private EmotionRepository emotionRepository;
    @Autowired
    private Auth auth;
    private static YouTube youtube;

    public List<Youtube> getYoutubeContentsByQuery(String query, Integer memberNo) {
        List<Youtube> resultVideos = new ArrayList<>();

        try {
            youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, request -> {})
                    .setApplicationName("YOUR_APP_NAME").build();
            YouTube.Search.List search = youtube.search().list("id,snippet");
            search.setKey(auth.getApiKey());
            search.setQ(query);
            search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
            search.setMaxResults(10L);

            List<SearchResult> searchResults = search.execute().getItems();

            Emotion relatedEmotion = emotionRepository.findFirstByMemberNoOrderByEmotionNoDesc(memberNo);

            for (SearchResult sr : searchResults) {
                Youtube video = new Youtube();
                if (relatedEmotion != null) {
                    video.setEmotion(relatedEmotion);
                    video.setMemberNo(memberNo);
                }
                String youtubeLink = "https://www.youtube.com/watch?v=" + sr.getId().getVideoId();
                video.setYoutubeLink(youtubeLink);
                video.setYoutubeTitle(sr.getSnippet().getTitle());
                youtubeRepository.save(video);
                resultVideos.add(video);
            }

        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error fetching YouTube videos", e);
        }
        return resultVideos;
    }
}

