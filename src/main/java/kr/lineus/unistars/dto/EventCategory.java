package kr.lineus.unistars.dto;

import lombok.Data;

@Data
public class EventCategory {
	
	private String id;
	private String categoryName;
	private String paymentType;
	private EventCategoryImage image;	
}
