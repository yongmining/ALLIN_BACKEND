package com.allin.filmface.choiceContents.book.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class BookDTO {

    private  int bookNo;
    private int category;
    private  String title;
    private  long IMG;
    private  String subTtile;
//   private  List<분석번호>


}
