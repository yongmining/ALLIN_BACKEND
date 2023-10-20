package com.allin.filmface.choiceContents.exercise.service;

import com.allin.filmface.choiceContents.auth.Auth;
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
    private Auth auth;
    private static YouTube youtube;

    public List<Exercise> getExerciseContentsByEmotionResult(String emotionResult) {
        return getExerciseContentsByQuery(emotionResult);
    }

    public List<Exercise> getExerciseContentsByQuery(String query) {
        List<Exercise> resultExercises = new ArrayList<>();

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
                Exercise exercise = new Exercise();
                String exerciseLink = "https://www.youtube.com/watch?v=" + sr.getId().getVideoId();
                exerciseLink = exerciseLink.replace("%0A", "");
                exercise.setExerciseLink(exerciseLink);
                exercise.setExerciseTitle(sr.getSnippet().getTitle());
                exerciseRepository.save(exercise);
                resultExercises.add(exercise);
            }

        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error fetching YouTube videos for exercises", e);
        }
        return resultExercises;
    }
}
