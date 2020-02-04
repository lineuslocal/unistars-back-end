package kr.lineus.unistars.dto;

import lombok.Data;

@Data
public class User {

    private String id;
	private String email;
	private String password;
	private String fullName;
	private String phoneNumber;
	private String gender;
	private String zipCode;
	private String address;
	private String city;
	private String birthdate;
	private String roles;
	private int level;
	
}
