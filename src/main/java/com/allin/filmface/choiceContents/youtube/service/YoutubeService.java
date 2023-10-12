package com.allin.filmface.choiceContents.youtube.service;
import com.allin.filmface.choiceContents.auth.Auth;
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
    private Auth auth; // Auth 클래스의 인스턴스를 주입 받습니다.
    private static YouTube youtube;

    public List<Youtube> getYoutubeContentsByEmotion(String emotionResult) {
        List<Youtube> resultVideos = new ArrayList<>();

        try {
            youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, request -> {})
                    .setApplicationName("YOUR_APP_NAME").build();
            YouTube.Search.List search = youtube.search().list("id,snippet");
            search.setKey(auth.getApiKey()); // 주입 받은 Auth 인스턴스를 사용하여 API 키를 가져옵니다.
            search.setQ(emotionResult + " 영상");
            search.setType("video");
            search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
            search.setMaxResults(10L);

            List<SearchResult> searchResults = search.execute().getItems();

            for (SearchResult sr : searchResults) {
                Youtube video = new Youtube();
                String youtubeLink = "https://www.youtube.com/watch?v=" + sr.getId().getVideoId();
                youtubeLink = youtubeLink.replace("%0A", ""); // 링크에서 %0A 제거
                video.setYoutubeLink(youtubeLink);
                video.setYoutubeTitle(sr.getSnippet().getTitle());
                youtubeRepository.save(video);
                resultVideos.add(video);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error fetching YouTube videos", e);
        }
        return resultVideos;
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(500).body(ex.getMessage());
    }
}