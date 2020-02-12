package kr.lineus.unistars.service;

import java.util.List;

import kr.lineus.unistars.converter.FAQConverter;
import kr.lineus.unistars.dto.FAQSubject;
import kr.lineus.unistars.repository.FAQSubjectRepository;

public class FAQServiceImpl implements FAQService, ControllerTestingService{

	FAQSubjectRepository faqSubjectRepo;
	
	@Override
	public void beforeEveryTest() {
		
		
	}

	@Override
	public void afterEveryTest() {
		
		
	}

	@Override
	public List<FAQSubject> loadAll() {
		return FAQConverter.faqSubjectEntityToDtoList(faqSubjectRepo.findAll());
	}

}
