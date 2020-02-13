package kr.lineus.unistars.entity;

import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "keyword")
@Data
public class FAQKeywordEntity {

	@Id
	@GeneratedValue
	private UUID id;	
	private String keyword;
	@Column(length = 5000)
	private String note;
	private LocalDate createdDate = LocalDate.now();
	
}
