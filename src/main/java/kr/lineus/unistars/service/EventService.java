package kr.lineus.unistars.service;

import java.util.List;

import kr.lineus.unistars.dto.EventCategory;

public interface EventService extends ControllerTestingService {

	List<EventCategory> loadAllCategories();

}
