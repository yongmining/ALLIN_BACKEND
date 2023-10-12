//package com.allin.filmface.niceTable.controller;
//
//import com.allin.filmface.niceTable.entity.Nice;
//import com.allin.filmface.niceTable.repository.NiceRepository;
//import com.allin.filmface.niceTable.service.NiceService;
//import lombok.AllArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/api/v1")
//@AllArgsConstructor
//public class NiceController {
//    @Autowired
//    private NiceService niceService;
//
//    @PostMapping("/increment")
//    public ResponseEntity<String> incrementNice(@RequestParam int searchNo) {
//        Nice updatedNice = niceService.incrementNice(searchNo);
//        if (updatedNice != null) {
//            return ResponseEntity.ok("Niced!");
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @GetMapping("/get")
//    public ResponseEntity<Nice> getNices(@RequestParam int searchNo) {
//        Nice nice = niceService.getNiceBySearchNo(searchNo);
//        if (nice != null) {
//            return ResponseEntity.ok(nice);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//}