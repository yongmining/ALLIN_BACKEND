package com.allin.filmface.choiceContents.exercise.service;

import com.allin.filmface.choiceContents.auth.Auth;
import com.allin.filmface.choiceContents.exercise.entity.GuestExercise;
import com.allin.filmface.choiceContents.exercise.repository.GuestExerciseRepository;
import com.allin.filmface.emotion.entity.GuestEmotion;
import com.allin.filmface.emotion.repository.GuestEmotionRepository;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GuestExerciseService {

    @Autowired
    private GuestExerciseRepository guestExerciseRepository;
    @Autowired
    private GuestEmotionRepository guestEmotionRepository;
    @Autowired
    private Auth auth;
    private static YouTube youtube;

    public List<GuestExercise> getGuestExerciseContentsByQuery(String query, Integer guestNo) {
        List<GuestExercise> resultVideos = new ArrayList<>();

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
                String guestExerciseTitle = sr.getSnippet().getTitle();

                // Check if video with the same title already exists in the database for the user.
                List<GuestExercise> existingVideos = guestExerciseRepository.findByGuestExerciseTitleAndGuestNo(guestExerciseTitle, guestNo);

                if (existingVideos.isEmpty()) {
                    GuestExercise video = new GuestExercise();
                    if (guestRelatedEmotion != null) {
                        video.setGuestEmotion(guestRelatedEmotion);
                        video.setGuestNo(guestNo);
                    }
                    String guestExerciseLink = "https://www.youtube.com/watch?v=" + sr.getId().getVideoId();
                    video.setGuestExerciseLink(guestExerciseLink);
                    video.setGuestExerciseTitle(guestExerciseTitle);
                    video.setThumbnailUrl(sr.getSnippet().getThumbnails().getDefault().getUrl()); // 이미지 URL 설정
                    guestExerciseRepository.save(video);
                    resultVideos.add(video);
                } else {
                    // 이미 DB에 존재하는 비디오도 결과 리스트에 추가
                    resultVideos.addAll(existingVideos);
                }
            }

        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error fetching exercise videos", e);
        }
        return resultVideos;
    }
}
