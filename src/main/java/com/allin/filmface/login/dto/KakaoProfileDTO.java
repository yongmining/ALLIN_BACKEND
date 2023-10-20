package com.allin.filmface.login.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoProfileDTO {

	private long id;
	private Properties properties;

	@AllArgsConstructor
	@NoArgsConstructor
	@Setter
	@Getter
	@ToString
	@JsonIgnoreProperties(ignoreUnknown = true)
	public class Properties {
		private String nickname;
	}
}