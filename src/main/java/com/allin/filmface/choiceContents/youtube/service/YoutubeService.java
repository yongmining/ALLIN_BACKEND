package com.allin.filmface.choiceContents.youtube.service;

import com.allin.filmface.choiceContents.auth.Auth;
import com.allin.filmface.emotion.entity.Emotion;
import com.allin.filmface.emotion.repository.EmotionRepository;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchResult;
import com.allin.filmface.choiceContents.youtube.entity.Youtube;
import com.allin.filmface.choiceContents.youtube.repository.YoutubeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class YoutubeService {

    @Autowired
    private YoutubeRepository youtubeRepository;
    @Autowired
    private EmotionRepository emotionRepository;
    @Autowired
    private Auth auth;
    private static YouTube youtube;




    @Transactional
    public void incrementNiceCountByLink(String youtubeLink) {
        Optional<Youtube> youtubeOptional = youtubeRepository.findByYoutubeLink(youtubeLink);
        if (youtubeOptional.isPresent()) {
            Youtube youtube = youtubeOptional.get();
            youtube.setNiceCount(youtube.getNiceCount() + 1);
            youtubeRepository.save(youtube);
        } else {
            throw new RuntimeException("Youtube not found");
        }
    }

    @Transactional
    public void decrementNiceCountByLink(String youtubeLink) {
        Optional<Youtube> youtubeOptional = youtubeRepository.findByYoutubeLink(youtubeLink);
        if (youtubeOptional.isPresent()) {
            Youtube youtube = youtubeOptional.get();
            youtube.setNiceCount(youtube.getNiceCount() - 1);
            youtubeRepository.save(youtube);
        } else {
            throw new RuntimeException("Youtube not found");
        }
    }

    public List<Youtube> getYoutubeContentsByQuery(String query, Integer memberNo) {
        List<Youtube> resultVideos = new ArrayList<>();

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

            Emotion relatedEmotion = emotionRepository.findFirstByMemberNoOrderByEmotionNoDesc(memberNo);

            for (SearchResult sr : searchResults) {
                String youtubeTitle = sr.getSnippet().getTitle();

                // Check if video with the same title already exists in the database for the user.
                List<Youtube> existingVideos = youtubeRepository.findByYoutubeTitleAndMemberNo(youtubeTitle, memberNo);

                if (existingVideos.isEmpty()) {
                    Youtube video = new Youtube();
                    if (relatedEmotion != null) {
                        video.setEmotion(relatedEmotion);
                        video.setMemberNo(memberNo);
                    }
                    String youtubeLink = "https://www.youtube.com/watch?v=" + sr.getId().getVideoId();
                    video.setYoutubeLink(youtubeLink);
                    video.setYoutubeTitle(youtubeTitle);
                    video.setThumbnailUrl(sr.getSnippet().getThumbnails().getDefault().getUrl()); // 이미지 URL 설정
                    youtubeRepository.save(video);
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
