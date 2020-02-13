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
	@Column(length = 50000)
	private String note;
	
	@OneToMany(mappedBy = "subject", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
	List<FAQCategoryEntity> categories = new ArrayList<FAQCategoryEntity>();
	
	public void addFAQCategoryEntity(FAQCategoryEntity en) {
		en.setSubject(this);
		this.categories.add(en);
	}
}	
