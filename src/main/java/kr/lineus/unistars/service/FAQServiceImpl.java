package kr.lineus.unistars.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.lineus.unistars.converter.FAQConverter;
import kr.lineus.unistars.dto.ELevel;
import kr.lineus.unistars.dto.FAQSubject;
import kr.lineus.unistars.entity.FAQCategoryEntity;
import kr.lineus.unistars.entity.FAQEntity;
import kr.lineus.unistars.entity.FAQImageEntity;
import kr.lineus.unistars.entity.FAQKeywordEntity;
import kr.lineus.unistars.entity.FAQProductEntity;
import kr.lineus.unistars.entity.FAQSubjectEntity;
import kr.lineus.unistars.repository.FAQImageRepository;
import kr.lineus.unistars.repository.FAQRepository;
import kr.lineus.unistars.repository.FAQSubjectRepository;
import kr.lineus.unistars.repository.KeywordRepository;

@Service
@Transactional
@Qualifier("faqService")
public class FAQServiceImpl implements FAQService, ControllerTestingService {

	@Autowired
	FAQSubjectRepository faqSubjectRepo;
	
	@Autowired
	KeywordRepository keywordRepo;
	
	FAQRepository faqRepo;
	
	FAQImageRepository faqImageRepo;
	
	@Override
	public void beforeEveryTest() {

		//add some keywords
		List<FAQKeywordEntity> keys = new ArrayList<FAQKeywordEntity>(); 
		for(int i=0; i <= 10;i++) {
			FAQKeywordEntity kw = new FAQKeywordEntity();
			kw.setKeyword("keyword" + i);
			kw.setNote("note for keyword" + i);
			keys.add(keywordRepo.save(kw));
		}
		List<FAQSubjectEntity> subjects = new ArrayList<FAQSubjectEntity>();
		//add 2 FAQ
		for(int i=0;i<2;i++) {
		
			FAQSubjectEntity faqSubject = new FAQSubjectEntity();
			faqSubject.setName("subject" + i);
			
			FAQCategoryEntity faqCat = new FAQCategoryEntity();
			faqCat.setName("cat" + i);
			faqSubject.addFAQCategoryEntity(faqCat);
			
			FAQProductEntity prodEn = new FAQProductEntity();
			prodEn.setName("prod" + i);
			faqCat.addFAQProductEntity(prodEn);
			
			FAQEntity faq = new FAQEntity();
			faq.setContent("This is a dummy FAQ");
			faq.setCreatedDate(LocalDate.now());
			faq.setLevel(ELevel.Advanced);
			faq.setTitle("title" + i);
			
			FAQKeywordEntity k = keys.get(new Random(0).nextInt(5));
			faq.getKeywords().add(k);
			
			k =  keys.get(new Random(6).nextInt(9));
			faq.getKeywords().add(k);
			prodEn.addFAQEntity(faq);
			
			
			FAQImageEntity img = new FAQImageEntity();
			img.setFileName("fileName" + i);
			img.setFileType("PNG");
			
			faq.addImage(img);
			
			
			
			subjects.add(faqSubjectRepo.save(faqSubject));
		}
		
	}

	@Override
	public void afterEveryTest() {
		faqSubjectRepo.deleteAll();
		keywordRepo.deleteAll();
		faqImageRepo.deleteAll();
		faqRepo.deleteAll();
	}
	
	public FAQSubject saveFAQSubject(FAQSubject subj) {
		FAQSubjectEntity en = FAQConverter.getInstance().faqSubjectDtoToEntity(subj);
		return FAQConverter.getInstance().faqSubjectEntityToDto(faqSubjectRepo.save(en));
	}

	@Override
	public List<FAQSubjectEntity> loadAll() {
		return faqSubjectRepo.findAll();
	}

	@Override
	public List<FAQKeywordEntity> loadAllKeywords() {
		return keywordRepo.findAll();
	}

	@Override
	public List<FAQSubjectEntity> saveAllSubjects(List<FAQSubjectEntity> subjects) {
		return faqSubjectRepo.saveAll(subjects);
	}

	@Override
	public FAQEntity saveFAQ(FAQEntity faq) {
		return faqRepo.save(faq) ;
	}

	@Override
	public FAQImageEntity saveFAQImage(FAQImageEntity en) {
		return faqImageRepo.save(en);
	}

	@Override
	public FAQImageEntity getFAQImage(String id) {
		return faqImageRepo.getOne(UUID.fromString(id));
	}

	@Override
	public FAQKeywordEntity saveFAQkeyword(FAQKeywordEntity kw) {
		return keywordRepo.save(kw);
	}

	@Override
	public FAQEntity getFAQ(String id) {
		return faqRepo.getOne(UUID.fromString(id));
	}

	@Override
	public FAQSubjectEntity saveSubject(FAQSubjectEntity subj) {
		return faqSubjectRepo.save(subj);
	}

	@Override
	public FAQSubjectEntity getSubject(String id) {
		return faqSubjectRepo.getOne(UUID.fromString(id));
	}

	@Override
	public void deleteKeywords(@Valid List<String> ids) {
		keywordRepo.deleteByIdIn(ids);
	}

}
