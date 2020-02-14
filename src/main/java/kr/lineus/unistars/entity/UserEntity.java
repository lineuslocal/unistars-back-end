package kr.lineus.unistars.entity;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import kr.lineus.unistars.dto.UserLevel;
import lombok.Data;

@Entity
@Table(name = "\"user\"")
@Data
public class UserEntity {

    @Id
    @GeneratedValue
    private UUID id;
    private String username;
	private String email;
	private String password;
	private String fullname;
	private String phonenumber;
	private String gender;
	private String address;
	private String city;
	private String job;
	private String birthdate;
	private String roles;
	@Enumerated(EnumType.STRING)
	private UserLevel level;
	
	
}
