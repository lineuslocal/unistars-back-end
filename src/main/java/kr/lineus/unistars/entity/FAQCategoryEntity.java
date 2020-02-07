package kr.lineus.unistars.entity;

import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "category")
public class FAQCategoryEntity {
	@Id
	@GeneratedValue
	private UUID id;
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "subject_id", nullable = false)
	private FAQSubjectEntity subject;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
	private List<FAQProductEntity> products;
}
