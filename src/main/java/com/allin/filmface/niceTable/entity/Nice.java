package com.allin.filmface.niceTable.entity;

import com.allin.filmface.search.entity.Search;
import lombok.*;
import org.hibernate.annotations.Comment;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity(name= "Nice")
@Table(name = "NICETABLE")
public class Nice {

    @Id
    @GeneratedValue()
    @Column(name = "NICE_NO")
    @Comment("좋아요 번호")
    private int niceNo;

    @Column(name = "NICE_COUNT")
    private int niceCount;



}
