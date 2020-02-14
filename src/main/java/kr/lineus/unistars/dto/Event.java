package kr.lineus.unistars.dto;

import java.time.LocalTime;

import lombok.Data;

@Data
public class Event {

	
	private String id;
	private String eventName;
	private String lecturer;
	private int maxParticipant;
	private int currentParticipant;
	private LocalTime startTime;
	private LocalTime endTime;
	private LocalTime startRegTime;
	private LocalTime endRegTime;
	private String description;
}
