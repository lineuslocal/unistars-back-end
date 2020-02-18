package kr.lineus.unistars.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.lineus.unistars.converter.EventConverter;
import kr.lineus.unistars.dto.EventCategory;
import kr.lineus.unistars.entity.EventCategoryEntity;
import kr.lineus.unistars.entity.EventEntity;
import kr.lineus.unistars.repository.EventCategoryRepository;

@Service
@Transactional
@Qualifier("eventService")
public class EventServiceImpl implements EventService {

	@Autowired
	EventCategoryRepository eventCatRepo;
	
	@Override
	public void beforeEveryTest() {
		
		
		EventEntity event = new EventEntity();
		event.setEventName("Event 1");
		event.setLecturer("Thomas Edition");
		event.setCategory(eventCatRepo.findAll().get(0));
		event.setCurrentParticipant(20);
		event.setDescription("");
		
	}

	@Override
	public void afterEveryTest() {
		// TODO Auto-generated method stub
		
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

}
