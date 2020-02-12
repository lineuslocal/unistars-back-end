package kr.lineus.unistars.service;

import java.util.List;

import kr.lineus.unistars.dto.FAQSubject;

public interface FAQService extends ControllerTestingService {

	List<FAQSubject> loadAll();
}
