//import com.allin.filmface.emotion.entity.Picture;
//import com.allin.filmface.emotion.service.PictureService;
////import com.allin.filmface.member.entity.Member;
//import io.swagger.annotations.Api;
//import lombok.AllArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.server.ResponseStatusException;
//import org.springframework.http.HttpStatus;
//
//import java.util.NoSuchElementException;
//import java.security.Principal; // Principal import 추가
//
//@Api(tags = "감정분석 관련 API")
//@RestController
//@RequestMapping("/api/v1")
//@AllArgsConstructor
//public class PictureController {
//
//    private final PictureService pictureService;
//
//    @GetMapping("/pictures/{id}")
//    public ResponseEntity<Picture> getPicture(@PathVariable Long id) {
//        try {
//            Picture picture = pictureService.findById(id);
//            return ResponseEntity.ok(picture);
//        } catch (NoSuchElementException e) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Picture not found.");
//        }
//    }
//
//    @PostMapping("/upload")
//    public ResponseEntity<String> uploadPicture(@RequestParam("image") MultipartFile file, Principal principal) {
//        try {
//            String username = principal.getName(); // 현재 로그인한 사용자 이름 (username)을 얻음
//            Picture savedPicture = pictureService.save(file, username);
//            return ResponseEntity.ok("File uploaded successfully. Picture ID: " + savedPicture.getPictureNo());
//        } catch (Exception e) {
//            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to upload the file.");
//        }
//    }
//}