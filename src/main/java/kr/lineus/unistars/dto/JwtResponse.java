package kr.lineus.unistars.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {
	private String accessToken;
	private String tokenType = "Bearer";
	private String id;
	private String userName;
	private String email;
	private String phoneNumber;
	private List<String> roles;
	private String level; 
}
