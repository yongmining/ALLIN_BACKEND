package com.allin.filmface.picture.entity;

import com.allin.filmface.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity(name= "Picture")
@Table(name = "PICTURE")
public class Picture {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "PICTURE_NO")
        private int pictureNo;

    @ManyToOne
    @JoinColumn(name = "MEMBER_NO", referencedColumnName = "MEMBER_NO")
    @JsonBackReference
    private Member member;

    }
