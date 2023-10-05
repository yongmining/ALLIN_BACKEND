package search.entity;

import lombok.*;
import org.hibernate.annotations.Comment;

import javax.persistence.*;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity(name= "Search")
@Table(name = "SEARCH")
@SequenceGenerator(name = "SEARCH_SEQ_GENERATOR", sequenceName = "SEQ_SEARCH_ID", initialValue = 1, allocationSize = 50)

public class SearchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEARCH_SEQ_GENERATOR")
    @Column(name = "SEARCH_NO")
    @Comment("조회번호")
    private int searchNo;

    @Column(name = "SEARCH_AGE")
    private int searchAge;

    @Column(name = "TOTAL_NO")
    private int totalNo;

//    @OneToMany
//    @JoinColumn(name= "MEMBER_NO", referencedColumnName = "MEMBER_NO" )
//    @Comment("멤버아이디")
//    private Member member;
}
