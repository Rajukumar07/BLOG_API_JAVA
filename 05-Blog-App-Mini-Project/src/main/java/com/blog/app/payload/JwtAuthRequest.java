package com.blog.app.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class JwtAuthRequest {

	private String username;

	private String password;
}
