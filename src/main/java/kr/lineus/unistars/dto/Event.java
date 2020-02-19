package kr.lineus.unistars.dto;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;

import lombok.Data;

@Data
public class Event {

	private String id;
	@NotBlank
	private String eventName;
	@NotBlank
	private String lecturer;
	@Positive
	private int maxParticipant;
	@Positive
	private int currentParticipant;
	@PastOrPresent
	private LocalTime startDatetime;
	private LocalTime endDatetime;
	private LocalTime startApplyDatetime;
	private LocalTime endApplyDatetime;
	private String description;
	private Set<EventImage> images = new HashSet<>();
	private Set<EventAdditionalInfo> additionalInfos = new HashSet<EventAdditionalInfo>();
	private Set<EventSurvey> eventSurveys = new HashSet<EventSurvey>();
}
