package com.allin.filmface.member.entity;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity(name = "Guest")
@Table(name = "GUEST")
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GUEST_NO")
    private int guestNo;

    @Column(name = "GUEST_NICKNAME", nullable = false)
    private String guestNickname;

    @Column(name = "GUEST_AGE")
    private int guestAge;

    @Column(name = "GUEST_IMG")
    private String guestImage;

    @Column(name = "GUEST_GENDER")
    private String guestGender;

    @Column(name = "SOCIAL_LOGIN", nullable = false)
    private String socialLogin;

    @Column(name = "SOCIAL_CODE", nullable = false)
    private String socialCode;

    @Column(name = "ACCESS_TOKEN", nullable = false)
    private String accessToken;


}
