package kr.lineus.unistars.entity;

import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
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
@Table(name = "product")
public class FAQProductEntity {
	@Id
	@GeneratedValue
	private UUID id;
	private String name;
	
	@ManyToOne
	@JoinColumn(name="category_id", nullable = false)
	private FAQCategoryEntity category;
	
	@OneToMany(mappedBy = "product", cascade = {CascadeType.ALL})
	List<FAQEntity> faqs;
	
}
