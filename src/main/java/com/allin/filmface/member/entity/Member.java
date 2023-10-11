package com.allin.filmface.member.entity;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity(name = "Member")
@Table(name = "MEMBER")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_NO")
    private int memberNo;

    @Column(name = "MEMBER_NICKNAME", unique = true, nullable = false)
    private String memberNickname;

    @Column(name = "MEMBER_AGE")
    private long memberAge;

    @Column(name = "MEMBER_IMG")
    private String memberImage;

    @Column(name = "MEMBER_GENDER")
    private String memberGender;

    @Column(name = "SOCIAL_LOGIN", nullable = false)
    private String socialLogin;

    @Column(name = "SOCIAL_ID", nullable = false)
    private String socialId;

    @Column(name = "ACCESS_TOKEN", nullable = false)
    private String accessToken;

    @Column(name = "ACCESS_TOKEN_EXPIRE_DATE", nullable = false)
    private long accessTokenExpireDate;

    @Column(name = "REFRESH_TOKEN", nullable = false)
    private String refreshToken;

    @Column(name = "REFRESH_TOKEN_EXPIRE_DATE", nullable = false)
    private long refreshTokenExpireDate;

}