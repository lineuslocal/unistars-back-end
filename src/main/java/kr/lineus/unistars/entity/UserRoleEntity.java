package kr.lineus.unistars.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import kr.lineus.unistars.dto.ERole;
import lombok.Data;

@Data
@Entity
@Table(name = "role")
public class UserRoleEntity {
	@Id
	@GeneratedValue
	private UUID id;
	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private ERole name;
}