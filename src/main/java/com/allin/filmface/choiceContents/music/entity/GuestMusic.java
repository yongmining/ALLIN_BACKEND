

package com.allin.filmface.choiceContents.music.entity;
import com.allin.filmface.emotion.entity.Emotion;
import com.allin.filmface.emotion.entity.GuestEmotion;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

@Entity(name = "GuestMusic")
@Table(name = "GUEST_MUSIC")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GuestMusic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GUESTMUSIC_NO")
    private int guestMusicNo;

    @Column(name = "GUESTMUSIC_LINK")
    private String guestMusicLink;



    @Column(name = "GUESTMUSIC_TITLE")
    private String guestMusicTitle;

    @Column(name = "THUMBNAIL_URL") // 썸네일 이미지 URL 컬럼 추가
    private String thumbnailUrl;


    @ManyToOne
    @JoinColumn(name = "GUESTEMOTION_NO", referencedColumnName = "GUESTEMOTION_NO")
    @JsonBackReference
    private GuestEmotion guestEmotion;


    @Column(name = "GUEST_NO")
    private Integer guestNo;

}