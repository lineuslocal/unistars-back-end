package kr.lineus.unistars.entity;

import java.time.LocalTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "faq")
public class EventEntity {
	
	@Id
	@GeneratedValue
	private UUID id;
	private String eventName;
	private String lecturer;
	private int maxParticipant;
	private int currentParticipant;
	@Column(name = "start_datetime")
	private LocalTime startDateTime;
	@Column(name = "end_datetime")
	private LocalTime endDateTime;
	@Column(name = "start_apply_datetime")
	private LocalTime startApplyDateTime;
	@Column(name = "end_apply_datetime")
	private LocalTime endApplyDateTime;
	@Column(length = 5000)
	private String description;
	
}
