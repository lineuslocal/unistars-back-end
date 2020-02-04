package kr.lineus.unistars.entity;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "\"user\"")
@Data
public class UserEntity {

    @Id
    @GeneratedValue
    private UUID id;
    
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
