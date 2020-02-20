package kr.lineus.unistars.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.lineus.unistars.converter.EventConverter;
import kr.lineus.unistars.dto.EEventImageType;
import kr.lineus.unistars.dto.Event;
import kr.lineus.unistars.dto.EventCategory;
import kr.lineus.unistars.entity.ApplicantEntity;
import kr.lineus.unistars.entity.EventAdditionalInfoEntity;
import kr.lineus.unistars.entity.EventCategoryEntity;
import kr.lineus.unistars.entity.EventCategoryImageEntity;
import kr.lineus.unistars.entity.EventEntity;
import kr.lineus.unistars.entity.EventImageEntity;
import kr.lineus.unistars.entity.EventSurveyEntity;
import kr.lineus.unistars.repository.ApplicantRepository;
import kr.lineus.unistars.repository.EventCategoryRepository;
import kr.lineus.unistars.repository.EventImageRepository;
import kr.lineus.unistars.repository.EventRepository;

@Service
@Transactional
@Qualifier("eventService")
public class EventServiceImpl implements EventService {

	@Autowired
	EventCategoryRepository eventCatRepo;
	
	@Autowired
	EventRepository eventRepo;
	
	@Autowired
	EventImageRepository eventImageRepo;
	
	ApplicantRepository applicantRepo;
	
	@Override
	public void beforeEveryTest() {
		List<EventCategoryEntity> cats = eventCatRepo.findAll();
		for(int i=0; i<5; i++) {
			
			EventCategoryEntity cat = cats.get(i);
			EventEntity event = new EventEntity();
			event.setEventName("Spring Festival Event " + (i+1));
			event.setLecturer("Thomas Edward");
			event.setCategory(cat);
			event.setCurrentParticipant(20);
			event.setDescription("This is an annual occasion where people celeberate their harvest festival time");
			event.setStartDateTime(LocalDateTime.now());
			event.setEndDateTime(LocalDateTime.now().plusDays(5));
			event.setStartApplyDateTime(LocalDateTime.now().plusDays(1));
			event.setEndApplyDateTime(LocalDateTime.now().plusDays(4));
			event.setMaxParticipant(100);

			EventAdditionalInfoEntity additionalInfo1 = new EventAdditionalInfoEntity();
			additionalInfo1.setEvent(event);
			additionalInfo1.setQuestion("Enter your religion");
			additionalInfo1.setRequired(false);
			event.getAdditionalInfos().add(additionalInfo1);
			EventAdditionalInfoEntity additionalInfo2 = new EventAdditionalInfoEntity();
			additionalInfo2.setEvent(event);
			additionalInfo2.setQuestion("Enter your sponsor");
			additionalInfo2.setRequired(true);
			event.getAdditionalInfos().add(additionalInfo2);

			EventSurveyEntity survey = new EventSurveyEntity();
			survey.setQuestion("What do you think about the event?");
			survey.setSelections("good, bad");
			survey.setEvent(event);
			event.getSurveys().add(survey);

			EventImageEntity image_profile = new EventImageEntity();
			image_profile.setFileName(i + "profile.png");
			image_profile.setFileType("PNG");
			image_profile.setImageType(EEventImageType.Profile);
			image_profile.setEvent(event);
			event.getImages().add(image_profile);
			EventImageEntity image_reg = new EventImageEntity();
			image_reg.setFileName(i + "reg.png");
			image_reg.setFileType("PNG");
			image_reg.setImageType(EEventImageType.Reg);
			image_reg.setEvent(event);
			event.getImages().add(image_reg);
			EventImageEntity image_guide = new EventImageEntity();
			image_guide.setFileName(i+"guide.png");
			image_guide.setFileType("PNG");
			image_guide.setImageType(EEventImageType.Guide);
			image_guide.setEvent(event);
			event.getImages().add(image_guide);
			EventImageEntity image_lecture = new EventImageEntity();
			image_lecture.setFileName(i+"lecture.png");
			image_lecture.setFileType("PNG");
			image_lecture.setImageType(EEventImageType.Lecture);
			image_lecture.setEvent(event);
			event.getImages().add(image_lecture);

			if(cat.getImage()==null) {
				EventCategoryImageEntity iCat = new EventCategoryImageEntity();
				iCat.setCategory(cat);
				iCat.setFileName(image_profile.getFileName());
				iCat.setFileType(image_profile.getFileType());
				iCat.setData(image_profile.getData());
			} else {
				EventCategoryImageEntity iCat = cat.getImage();
				iCat.setCategory(cat);
				iCat.setFileName(image_profile.getFileName());
				iCat.setFileType(image_profile.getFileType());
				iCat.setData(image_profile.getData());
			}
			eventCatRepo.save(cat);
			eventRepo.save(event);
		}
		
	}

	@Override
	public void afterEveryTest() {
		eventRepo.deleteAll();
		
	}

	@PostConstruct
	public void doPostConstruct() {
		
		if(eventCatRepo.count()==0) {
			EventCategoryEntity eCat = new EventCategoryEntity();
			eCat.setCategoryName("1박2일 세미나");
			eCat.setPaymentType("Free");
			eventCatRepo.save(eCat);
			
			eCat = new EventCategoryEntity();
			eCat.setCategoryName("석세스코어");
			eCat.setPaymentType("Free");
			eventCatRepo.save(eCat);
			
			eCat = new EventCategoryEntity();
			eCat.setCategoryName("세븐헤빗");
			eCat.setPaymentType("Free");
			eventCatRepo.save(eCat);
			
			eCat = new EventCategoryEntity();
			eCat.setCategoryName("로컬/원데이");
			eCat.setPaymentType("Paid");
			eventCatRepo.save(eCat);
			
			
			eCat = new EventCategoryEntity();
			eCat.setCategoryName("컨벤션");
			eCat.setPaymentType("Paid");
			eventCatRepo.save(eCat);
		}
	}

	@Override
	public List<EventCategory> loadAllCategories() {
		List<EventCategoryEntity> catList = eventCatRepo.findAll();
		return EventConverter.getInstance().eventCatEntityToDtoList(catList);
	}

	@Override
	public List<Event> loadAllEventsByCategory(String categoryId) {
		return EventConverter.getInstance().eventEntityToDtoList(eventRepo.findAllByCategoryId(UUID.fromString(categoryId)));
	}

	@Override
	public EventEntity getEvent(String eventId) {
		return eventRepo.getOne(UUID.fromString(eventId));
	}

	@Override
	public EventImageEntity saveEventImage(EventImageEntity i) {
		return eventImageRepo.save(i);
	}

	@Override
	public EventEntity saveEvent(EventEntity eventEntity) {
		return eventRepo.save(eventEntity);
	}

	@Override
	public List<ApplicantEntity> getApplicants(String eventId) {
		
		
		return null;
	}

}
