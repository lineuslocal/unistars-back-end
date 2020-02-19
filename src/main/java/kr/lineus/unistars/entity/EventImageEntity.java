package kr.lineus.unistars.entity;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import kr.lineus.unistars.dto.EEventImageType;
import kr.lineus.unistars.dto.ELevel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "event_image")
public class EventImageEntity {
	@Id
	@GeneratedValue
	private UUID id;	
	private String fileName;
	private String fileType;
	@Enumerated(EnumType.STRING)
	private EEventImageType imageType;
	@ManyToOne
	@JoinColumn(name = "event_id", nullable = false, updatable = false)
	@EqualsAndHashCode.Exclude
	private EventEntity event;
	@Lob
	@EqualsAndHashCode.Exclude
	private byte[] data; 
}
