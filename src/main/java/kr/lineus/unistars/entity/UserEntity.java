package kr.lineus.unistars.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import kr.lineus.unistars.dto.ELevel;
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
	
	@Enumerated(EnumType.STRING)
	private ELevel level;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "user_role", 
				joinColumns = @JoinColumn(name = "user_id"), 
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	private List<UserRoleEntity> userRoles = new ArrayList<UserRoleEntity>();
	
}
