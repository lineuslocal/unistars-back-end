package kr.lineus.unistars.entity;

import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "subject")
@Data
public class FAQSubjectEntity {
	@Id
	@GeneratedValue
	private UUID id;	
	private String name;
	
	@OneToMany(mappedBy = "subject", cascade = {CascadeType.ALL})
	List<FAQCategoryEntity> categories;
}	
