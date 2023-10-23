package com.allin.filmface.choiceContents.exercise.service;

import com.allin.filmface.choiceContents.auth.Auth;
import com.allin.filmface.choiceContents.music.entity.Music;
import com.allin.filmface.emotion.entity.Emotion;
import com.allin.filmface.emotion.repository.EmotionRepository;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchResult;
import com.allin.filmface.choiceContents.exercise.entity.Exercise;
import com.allin.filmface.choiceContents.exercise.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExerciseService {

    @Autowired
    private ExerciseRepository exerciseRepository;
    @Autowired
    private EmotionRepository emotionRepository;

    @Autowired
    private Auth auth;
    private static YouTube youtube;

    public List<Exercise> getExerciseContentsByQuery(String query, Integer memberNo) {
        List<Exercise> resultExercises = new ArrayList<>();


        try {
            youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, request -> {})
                    .setApplicationName("YOUR_APP_NAME").build();

            YouTube.Search.List search = youtube.search().list("id,snippet");
            search.setKey(auth.getApiKey());
            search.setQ(query);
            search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
            search.setMaxResults(10L);
            search.setType("video"); // 영상만 검색
            search.setVideoCategoryId("10"); // 운동 카테고리

            List<SearchResult> searchResults = search.execute().getItems();

            Emotion relatedEmotion = emotionRepository.findFirstByMemberNoOrderByEmotionNoDesc(memberNo);

            for (SearchResult sr : searchResults) {
                Exercise exercise = new Exercise();
                if (relatedEmotion != null) {
                    exercise.setEmotion(relatedEmotion);
                    exercise.setMemberNo(memberNo);
                }
                String youtubeLink = "https://www.youtube.com/watch?v=" + sr.getId().getVideoId();
                exercise.setExerciseLink(youtubeLink);
                exercise.setExerciseTitle(sr.getSnippet().getTitle());
                exercise.setThumbnailUrl(sr.getSnippet().getThumbnails().getDefault().getUrl());
                exerciseRepository.save(exercise);
                resultExercises.add(exercise);
            }

        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error fetching YouTube musics", e);
        }

        return resultExercises;
    }
}
