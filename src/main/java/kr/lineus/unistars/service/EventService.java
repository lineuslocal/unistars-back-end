package kr.lineus.unistars.service;

import java.util.List;

import kr.lineus.unistars.dto.EEventImageType;
import kr.lineus.unistars.dto.Event;
import kr.lineus.unistars.dto.EventCategory;
import kr.lineus.unistars.entity.ApplicantEntity;
import kr.lineus.unistars.entity.EventCategoryEntity;
import kr.lineus.unistars.entity.EventEntity;
import kr.lineus.unistars.entity.EventImageEntity;

public interface EventService extends ControllerTestingService {

	List<EventCategoryEntity> loadAllCategories();
	
	List<EventEntity> loadAllEventsByCategory(String catId);
	
	EventEntity getEvent(String eventId);

	EventImageEntity saveEventImage(EventImageEntity i);
	
	EventEntity saveEvent(EventEntity eventEntity);
	
	List<ApplicantEntity> getApplicantsByEventId(String eventId);
	
	ApplicantEntity saveApplicantEntity(ApplicantEntity appEn);
	
	List<ApplicantEntity> getApplicantsByUserId(String userId);

	List<EventEntity> loadEventDetail(String eventId);

	void deleteEvents(List<String> ids);
	
	ApplicantEntity getApplicant(String applicantId);
	
	void deleteApplicant(String applicantId);
}
