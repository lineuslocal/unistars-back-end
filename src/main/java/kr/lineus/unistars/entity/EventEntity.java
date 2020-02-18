package kr.lineus.unistars.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
@Table(name = "event")
public class EventEntity {
	
	@Id
	@GeneratedValue
	private UUID id;
	private String eventName;
	private String lecturer;
	private int maxParticipant;
	private int currentParticipant;
	@Column(name = "start_datetime")
	private LocalDateTime startDateTime;
	@Column(name = "end_datetime")
	private LocalDateTime endDateTime;
	@Column(name = "start_apply_datetime")
	private LocalDateTime startApplyDateTime;
	@Column(name = "end_apply_datetime")
	private LocalDateTime endApplyDateTime;
	@Column(length = 5000)
	private String description;
	@OneToMany(mappedBy = "event", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<EventImageEntity> images = new HashSet<>();
	@ManyToOne
	@JoinColumn(name="event_catalog_id", nullable = false)
	private EventCategoryEntity category;
	
	@OneToMany(mappedBy = "event", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<EventAdditionalInfoEntity> additionalInfos = new HashSet<EventAdditionalInfoEntity>();
}
