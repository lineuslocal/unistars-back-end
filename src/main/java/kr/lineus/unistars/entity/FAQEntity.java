package kr.lineus.unistars.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import kr.lineus.unistars.dto.Level;
import lombok.Data;

@Data
@Entity
@Table(name = "faq")
public class FAQEntity {
	@Id
	@GeneratedValue
	private UUID id;
	private String title;
	private String content;
	@Enumerated(EnumType.STRING)
	private Level level;
	private LocalDate createdDate = LocalDate.now();	
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	private FAQProductEntity product;

}
