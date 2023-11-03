package com.allin.filmface.choiceContents.youtube.service;

import com.allin.filmface.choiceContents.auth.Auth;
import com.allin.filmface.choiceContents.youtube.entity.GuestYoutube;
import com.allin.filmface.choiceContents.youtube.entity.Youtube;
import com.allin.filmface.choiceContents.youtube.repository.GuestYoutubeRepository;
import com.allin.filmface.emotion.entity.GuestEmotion;
import com.allin.filmface.emotion.repository.GuestEmotionRepository;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class GuestYoutubeService {

    @Autowired
    private GuestYoutubeRepository guestYoutubeRepository;
    @Autowired
    private GuestEmotionRepository guestEmotionRepository;
    @Autowired
    private Auth auth;
    private static YouTube youtube;

    public List<GuestYoutube>getGuestYoutubeContentsByQuery(String query, Integer guestNo) {
        List<GuestYoutube> resultVideos = new ArrayList<>();

        try {
            youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, request -> {
            })
                    .setApplicationName("YOUR_APP_NAME").build();
            YouTube.Search.List search = youtube.search().list("id,snippet");
            search.setKey(auth.getApiKey());
            search.setQ(query);
            search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
            search.setMaxResults(10L);

            List<SearchResult> searchResults = search.execute().getItems();
            System.out.println("YouTube API Response: " + searchResults);


            GuestEmotion guestRelatedEmotion = guestEmotionRepository.findFirstByGuestNoOrderByGuestEmotionNoDesc(guestNo);

            for (SearchResult sr : searchResults) {
                String guestYoutubeTitle = sr.getSnippet().getTitle();

                // Check if video with the same title already exists in the database for the user.
                List<GuestYoutube> existingVideos = guestYoutubeRepository.findByGuestYoutubeTitleAndGuestNo(guestYoutubeTitle, guestNo);

                if (existingVideos.isEmpty()) {
                    GuestYoutube video = new GuestYoutube();
                    if (guestRelatedEmotion != null) {
                        video.setGuestEmotion(guestRelatedEmotion);
                        video.setGuestNo(guestNo);
                    }
                    String guestYoutubeLink = "https://www.youtube.com/watch?v=" + sr.getId().getVideoId();
                    video.setGuestYoutubeLink(guestYoutubeLink);
                    video.setGuestYoutubeTitle(guestYoutubeTitle);
                    video.setThumbnailUrl(sr.getSnippet().getThumbnails().getDefault().getUrl()); // 이미지 URL 설정
                    guestYoutubeRepository.save(video);
                    resultVideos.add(video);
                } else {
                    // 이미 DB에 존재하는 비디오도 결과 리스트에 추가
                    resultVideos.addAll(existingVideos);
                }
            }

        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error fetching YouTube videos", e);
        }
        return resultVideos;
    }
}
