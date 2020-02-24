package kr.lineus.unistars.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "faq_category")
public class FAQCategoryEntity {
	@Id
	@GeneratedValue
	private UUID id;
	private String name;
	private String krName;
	@Column(length = 50000)
	private String note;
	
	@ManyToOne
	@JoinColumn(name = "subject_id", nullable = false)
	private FAQSubjectEntity subject;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "category", fetch = FetchType.EAGER)
	private List<FAQProductEntity> products = new ArrayList<FAQProductEntity>();
	
	public void addFAQProductEntity(FAQProductEntity en) {
		en.setCategory(this);
		this.products.add(en);
	}
}
