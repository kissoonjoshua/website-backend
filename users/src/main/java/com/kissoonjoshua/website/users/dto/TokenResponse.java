package com.kissoonjoshua.website.users.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Data
public class TokenResponse {
	private String access_token;
    private String id_token;
    private String refresh_token;
    private String token_type;
    private int expires_in;
}
