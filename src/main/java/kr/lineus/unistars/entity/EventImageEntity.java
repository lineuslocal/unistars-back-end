package kr.lineus.unistars.entity;

import java.util.UUID;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

public class EventImageEntity {
	@Id
	@GeneratedValue
	private UUID id;	
	private String fileName;
	private String fileType;
	@Lob
	private byte[] data;
	
	
}
