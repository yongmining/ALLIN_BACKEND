//package com.allin.filmface.niceTable.service;
//
//import com.allin.filmface.niceTable.entity.Nice;
//import com.allin.filmface.niceTable.repository.NiceRepository;
//import com.allin.filmface.search.entity.SearchEntity;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Optional;
//
//@Service
//@Transactional
//public class NiceService {
//    @Autowired
//    private NiceRepository niceRepository;
//
//    public Nice getNiceBySearchNo(int searchNo) {
//        Optional<Nice> niceOptional = niceRepository.findBySearch_SearchNo(searchNo);
//        return niceOptional.orElse(null);
//    }
//
//    public Nice incrementNice(int searchNo) {
//        Optional<Nice> niceOptional = niceRepository.findBySearch_SearchNo(searchNo);
//
//        if (niceOptional.isPresent()) {
//            Nice nice = niceOptional.get();
//            nice.setNiceCount(nice.getNiceCount() + 1);
//            return niceRepository.save(nice);
//        } else {
//            Nice newNice = new Nice();
//            // setSearchNo 메소드 대신 직접 search 엔티티를 생성하여 연결
//            SearchEntity searchEntity = new SearchEntity();
//            searchEntity.setSearchNo(searchNo);
//            newNice.setSearch(searchEntity); // Nice 엔티티와 연결
//            newNice.setNiceCount(1);
//            return niceRepository.save(newNice);
//        }
//    }
//}
