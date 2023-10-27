package com.allin.filmface.choiceContents.exercise.service;

import com.allin.filmface.choiceContents.auth.Auth;
import com.allin.filmface.choiceContents.exercise.entity.Exercise;
import com.allin.filmface.choiceContents.exercise.repository.ExerciseRepository;
import com.allin.filmface.emotion.entity.Emotion;
import com.allin.filmface.emotion.repository.EmotionRepository;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchResult;
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
            search.setType("video");
            search.setVideoCategoryId("10");

            List<SearchResult> searchResults = search.execute().getItems();

            Emotion relatedEmotion = emotionRepository.findFirstByMemberNoOrderByEmotionNoDesc(memberNo);

            for (SearchResult sr : searchResults) {
                String exerciseTitle = sr.getSnippet().getTitle();

                // Check if exercise with the same title already exists in the database for the user.
                List<Exercise> existingExercises = exerciseRepository.findByExerciseTitleAndMemberNo(exerciseTitle, memberNo);

                if (existingExercises.isEmpty()) {
                    Exercise exercise = new Exercise();
                    if (relatedEmotion != null) {
                        exercise.setEmotion(relatedEmotion);
                        exercise.setMemberNo(memberNo);
                    }
                    String youtubeLink = "https://www.youtube.com/watch?v=" + sr.getId().getVideoId();
                    exercise.setExerciseLink(youtubeLink);
                    exercise.setExerciseTitle(exerciseTitle);
                    exercise.setThumbnailUrl(sr.getSnippet().getThumbnails().getDefault().getUrl());
                    exerciseRepository.save(exercise);
                    resultExercises.add(exercise);
                } else {
                    // Add already existing exercises to the result list.
                    resultExercises.addAll(existingExercises);
                }
            }

        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error fetching YouTube exercises", e);
        }

        return resultExercises;
    }
}
