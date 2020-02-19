package kr.lineus.unistars.entity;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@Entity
@Table(name = "event_category_image")
public class EventCategoryImageEntity {
	@Id
	@GeneratedValue
	private UUID id;	
	private String fileName;
	private String fileType;
	@OneToOne
	@JoinColumn(name = "category_id", unique = true, nullable = false, updatable = false)
	@EqualsAndHashCode.Exclude
	private EventCategoryEntity category;
	@Lob
	private byte[] data; 
}
