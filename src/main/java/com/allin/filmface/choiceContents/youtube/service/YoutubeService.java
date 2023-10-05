//package com.allin.filmface.choiceContents.youtube.service;
//
//import com.allin.filmface.choiceContents.youtube.dto.YoutubeDTO;
//import com.allin.filmface.choiceContents.youtube.entity.Youtube;
//import com.allin.filmface.choiceContents.youtube.repository.YoutubeRepository;
//import com.google.api.services.youtube.YouTube;
//import com.google.api.services.youtube.model.SearchResult;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class YoutubeService {
//
//    @Autowired
//    private YoutubeRepository youtubeRepository;
//
//    private YouTube youtube;
//
//    public YoutubeService() {
//        youtube = new YouTube.Builder(/*...*/) // 이 부분에 적절한 HTTP Transport 및 JSON Factory 필요
//                .setApplicationName("YOUR_APP_NAME")
//                .build();
//    }
//
//    public YoutubeDTO saveYoutubeData(YoutubeDTO youtubeDTO) {
//        Youtube youtubeEntity = convertToEntity(youtubeDTO);
//        Youtube savedYoutube = youtubeRepository.save(youtubeEntity);
//        return convertToDTO(savedYoutube);
//    }
//
//    public Optional<YoutubeDTO> getYoutubeData(int id) {
//        Optional<Youtube> youtube = youtubeRepository.findById(id);
//        return youtube.map(this::convertToDTO);
//    }
//
//    public List<SearchResult> searchYoutube(String keyword) throws Exception {
//        YouTube.Search.List search = youtube.search().list("id,snippet");
//        search.setKey("YOUR_API_KEY");
//        search.setQ(keyword);
//        search.setType("video");
//        return search.execute().getItems();
//    }
//
//    public YoutubeDTO saveYoutubeDataFromSearch(String keyword) throws Exception {
//        List<SearchResult> results = searchYoutube(keyword);
//        YoutubeDTO youtubeDTO = new YoutubeDTO();
//        SearchResult result = results.get(0);
//        youtubeDTO.setYoutubeLink("https://www.youtube.com/watch?v=" + result.getId().getVideoId());
//        youtubeDTO.setYoutubeTitle(result.getSnippet().getTitle());
//        return saveYoutubeData(youtubeDTO);
//    }
//
//    // 사용자가 영상을 클릭했을 때 호출하는 메서드
//    public void incrementYoutubeViews(int youtubeNo) {
//        Optional<Youtube> youtubeOptional = youtubeRepository.findById(youtubeNo);
//        if(youtubeOptional.isPresent()) {
//            Youtube youtube = youtubeOptional.get();
//            youtube.setYoutubeViews(youtube.getYoutubeViews() + 1);
//            youtubeRepository.save(youtube);
//        }
//    }
//
//    private Youtube convertToEntity(YoutubeDTO youtubeDTO) {
//        Youtube youtube = new Youtube();
//        youtube.setYoutubeNo(youtubeDTO.getYoutubeNo());
//        youtube.setYoutubeLink(youtubeDTO.getYoutubeLink());
//        youtube.setYoutubeViews(youtubeDTO.getYoutubeViews());
//        youtube.setYoutubeTitle(youtubeDTO.getYoutubeTitle());
//        return youtube;
//    }
//
//    private YoutubeDTO convertToDTO(Youtube youtube) {
//        YoutubeDTO youtubeDTO = new YoutubeDTO();
//        youtubeDTO.setYoutubeNo(youtube.getYoutubeNo());
//        youtubeDTO.setYoutubeLink(youtube.getYoutubeLink());
//        youtubeDTO.setYoutubeViews(youtube.getYoutubeViews());
//        youtubeDTO.setYoutubeTitle(youtube.getYoutubeTitle());
//        return youtubeDTO;
//    }
//}
