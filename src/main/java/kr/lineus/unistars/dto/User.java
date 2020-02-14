package kr.lineus.unistars.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data    
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String id;
    private String username;
	private String email;
	private String password;
	private String fullname;
	private String phonenumber;
	private String gender;
	private String address;
	private String city;
	private String birthdate;
	private String job;
	private String roles;
	private UserLevel level;
	
}
