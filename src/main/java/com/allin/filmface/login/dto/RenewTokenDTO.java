package com.allin.filmface.login.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class RenewTokenDTO {

    private String token_type;
    private String access_token;
    private String id_token;
    private Integer expires_in;
    private String refresh_token;
    private Integer refresh_token_expires_in;


}