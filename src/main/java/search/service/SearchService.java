//package search.service;
//
//import lombok.AllArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Sort;
//import org.springframework.stereotype.Service;
//import search.dto.SearchDTO;
//import search.repository.SearchRepository;
//
//import java.awt.print.Pageable;
//import java.net.http.HttpHeaders;
//
//@Service
//@AllArgsConstructor
//public class SearchService {
//
//    private final SearchRepository searchRepository;
//
//
//    public Page<SearchDTO> findAllSearch(Pageable page, long searchNo) {
//
//        page = PageRequest.of(page.getPageNumber() <= 0 ? 0 : page.getPageNumber() -1, page.getPageSize(),
//                Sort.by("searchNo"));
//
//        Page<Search> searchs = searchRepository.findByMember()
//    }
//}
