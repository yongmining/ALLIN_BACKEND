package com.allin.filmface.aiSuggesion.entity;

import com.allin.filmface.choiceContents.youtube.entity.Youtube;
import com.allin.filmface.member.entity.Member;
import lombok.*;
import org.hibernate.annotations.Comment;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "AiSuggetion")
@Table(name = "AI_SUGGETION")
@Inheritance()
public class aiSuggetion {

    @Id
    @Column(name = "TOTAL_NO")
    @Comment("추천번호")
    private int totalNo;

    @ManyToOne
    @JoinColumn
    @Comment("유튜브 번호")
    private Youtube youtube;


//    @ManyToOne
//    @JoinColumn
//    @Comment("유튜브 번호")
//    private Youtube youtube;
//
//    @ManyToOne
//    @JoinColumn
//    @Comment("유튜브 번호")
//    private Youtube youtube;
//
//    @ManyToOne
//    @JoinColumn
//    @Comment("유튜브 번호")
//    private Youtube youtube;
//
//    @ManyToOne
//    @JoinColumn
//    @Comment("유튜브 번호")
//    private Youtube youtube;
//

    @ManyToOne
    @JoinColumn(name= "MEMBER_NO", referencedColumnName = "MEMBER_NO" )
    @Comment("멤버아이디")
    private Member member;
}
