
package com.allin.filmface.emotion.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PictureDTO {
    private byte[] image;  // 변경: InputStream 대신 byte[] 사용
    private String imageName;
}