package kr.lineus.unistars.service;

import java.util.List;

import kr.lineus.unistars.dto.EEventImageType;
import kr.lineus.unistars.dto.Event;
import kr.lineus.unistars.dto.EventCategory;
import kr.lineus.unistars.entity.EventEntity;
import kr.lineus.unistars.entity.EventImageEntity;

public interface EventService extends ControllerTestingService {

	List<EventCategory> loadAllCategories();
	
	List<Event> loadAllEventsByCategory(String catId);
	
	EventEntity getEvent(String eventId);

	EventImageEntity saveEventImage(EventImageEntity i);
	
	

}
