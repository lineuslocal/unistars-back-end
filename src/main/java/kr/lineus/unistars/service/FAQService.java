package kr.lineus.unistars.service;

import java.util.List;

import javax.validation.Valid;

import kr.lineus.unistars.dto.FAQKeyword;
import kr.lineus.unistars.entity.FAQEntity;
import kr.lineus.unistars.entity.FAQImageEntity;
import kr.lineus.unistars.entity.FAQKeywordEntity;
import kr.lineus.unistars.entity.FAQSubjectEntity;

public interface FAQService extends ControllerTestingService {

	List<FAQSubjectEntity> loadAll();
	
	FAQEntity getFAQ(String id);
	
	List<FAQKeywordEntity> loadAllKeywords();
	
	List<FAQSubjectEntity> saveAllSubjects(List<FAQSubjectEntity> subj);
	
	FAQEntity saveFAQ(FAQEntity faq);
	
	FAQImageEntity saveFAQImage(FAQImageEntity en);
	
	FAQImageEntity getFAQImage(String id);
	
	FAQKeywordEntity saveFAQkeyword(FAQKeywordEntity kw);
	
	FAQSubjectEntity saveSubject(FAQSubjectEntity subj);
	
	FAQSubjectEntity getSubject(String id);

	void deleteKeywords(@Valid List<String> ids);
}
