package kr.lineus.unistars.dto;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import lombok.Data;

@Data
public class Event {

	private String id;
	private String eventName;
	private String lecturer;
	private int maxParticipant;
	private int currentParticipant;
	private LocalTime startDatetime;
	private LocalTime endDatetime;
	private LocalTime startApplyDatetime;
	private LocalTime endApplyDatetime;
	private String description;
	private Set<EventImage> images = new HashSet<>();
	private Set<EventAdditionalInfo> additionalInfos = new HashSet<EventAdditionalInfo>();
}
