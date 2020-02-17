package kr.lineus.unistars.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;
	private String email;
	@NotBlank
	@Size(max = 120)
	private String password;
	private String fullname;
	private String phonenumber;
	private String gender;
	private String address;
	private String city;
	private String birthdate;
	private String job;
	private List<String> roles = new ArrayList<String>();
	private String level;
	
}
