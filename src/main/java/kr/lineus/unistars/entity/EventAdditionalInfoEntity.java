package kr.lineus.unistars.entity;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "event_additional_info")
public class EventAdditionalInfoEntity {

	@Id
	@GeneratedValue
	private UUID id;
	private String question;
	private boolean required;
	@ManyToOne
	@JoinColumn(name = "event_id", nullable = false)
	@EqualsAndHashCode.Exclude 
	private EventEntity event;
}
