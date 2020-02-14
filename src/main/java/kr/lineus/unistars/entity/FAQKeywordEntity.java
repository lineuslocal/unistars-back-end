package kr.lineus.unistars.entity;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "keyword")
public class FAQKeywordEntity {
	
	@Id
    @GeneratedValue
    private UUID id;
    private String keyword;
    @Column(length = 5000)
	private String note;
    private LocalDate createdDate = LocalDate.now();	
    
    /*
    @ManyToMany(mappedBy = "keywords", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<FAQEntity> faqs;*/
}
