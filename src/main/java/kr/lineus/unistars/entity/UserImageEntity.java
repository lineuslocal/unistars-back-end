package kr.lineus.unistars.entity;

import java.util.UUID;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

public class UserImageEntity {

	@Id
	@GeneratedValue
	private UUID id;	
	private String fileName;
	private String fileType;
	@Lob
	private byte[] data;
	
	@ManyToOne
	@JoinColumn(name="user_id", nullable = false)
	UserEntity user; 

		
}
