package kr.lineus.unistars.entity;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "event_image")
public class EventImageEntity {
	@Id
	@GeneratedValue
	private UUID id;	
	private String fileName;
	private String fileType;
	private String imageType;
	private String imagePath;
	@ManyToOne
	@JoinColumn(name = "event_id", nullable = false, updatable = false)
	private EventEntity event;
	@Lob
	private byte[] data; 
}
