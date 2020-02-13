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
@Table(name = "product")
public class FAQProductEntity {
	@Id
	@GeneratedValue
	private UUID id;
	private String name;
	@Column(length = 50000)
	private String note;
	
	@ManyToOne
	@JoinColumn(name="category_id", nullable = false)
	private FAQCategoryEntity category;
	
	@OneToMany(mappedBy = "product", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
	List<FAQEntity> faqs = new ArrayList<FAQEntity>();
	
	public void addFAQEntity(FAQEntity en) {
		en.setProduct(this);
		this.faqs.add(en);
	}
}
