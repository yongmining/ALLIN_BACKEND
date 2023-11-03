package com.allin.filmface.emotion.entity;

import com.allin.filmface.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity(name = "Picture")
@Table(name = "PICTURE")
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PICTURE_NO")
    private int pictureNo;

    @ManyToOne
    @JoinColumn(name = "MEMBER_NO", referencedColumnName = "MEMBER_NO")
    private Member member;
    //멤버하나 사진여러개
    @Lob
    @Column(name = "IMAGE")
    private byte[] image; // Byte array to store image data

    @Column(name = "IMAGE_NAME")
    private String imageName;

    // Constructors, getters, and setters

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
