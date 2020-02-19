package kr.lineus.unistars.entity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "event_category")
public class EventCategoryEntity {
	@Id
	@GeneratedValue
	private UUID id;
	private String categoryName;
	private String paymentType;
	@OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
	@EqualsAndHashCode.Exclude
	private Set<EventEntity> events = new HashSet<EventEntity>();
	@OneToOne(mappedBy = "category")
	@EqualsAndHashCode.Exclude
	private EventCategoryImageEntity image;
}
