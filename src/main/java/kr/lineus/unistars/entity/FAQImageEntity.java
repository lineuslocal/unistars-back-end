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

@Entity
@Table(name = "faq_image")
@Data
public class FAQImageEntity {
	@Id
	@GeneratedValue
	private UUID id;	
	private String fileName;
	private String fileType;
	@Lob
	private byte[] data;
	
	@ManyToOne
	@JoinColumn(name="faq_id", nullable = false)
	FAQEntity faq; 
}	
