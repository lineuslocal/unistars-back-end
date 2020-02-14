package kr.lineus.unistars.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import kr.lineus.unistars.dto.UserLevel;
import lombok.Data;

@Data
@Entity
@Table(name = "faq")
public class FAQEntity {
	@Id
	@GeneratedValue
	private UUID id;
	private String title;
	@Column(length = 50000)
	private String content;
	@Enumerated(EnumType.STRING)
	private UserLevel level;
	private boolean status;
	private LocalDate createdDate = LocalDate.now();	
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	private FAQProductEntity product;
	
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinTable(
			name="faq_keyword", 
			joinColumns = {@JoinColumn(name="faq_id")},
			inverseJoinColumns = {@JoinColumn(name="keyword_id")}
	)
	private List<FAQKeywordEntity> keywords = new ArrayList<FAQKeywordEntity>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "faq", cascade = CascadeType.ALL)
	private List<FAQImageEntity> images = new ArrayList<FAQImageEntity>();
	 
	public void addImage(FAQImageEntity image) {
		image.setFaq(this);
		this.images.add(image);
	}
}
