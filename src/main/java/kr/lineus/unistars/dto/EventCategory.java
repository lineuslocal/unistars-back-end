package kr.lineus.unistars.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class EventCategory {
	
	private String id;
	private String categoryName;
	private String paymentType;
	private EventCategoryImage image;	
	private List<Event> events = new ArrayList<Event>();
	
}
