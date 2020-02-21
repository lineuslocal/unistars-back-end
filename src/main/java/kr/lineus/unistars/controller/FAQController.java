package kr.lineus.unistars.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kr.lineus.unistars.converter.EventConverter;
import kr.lineus.unistars.converter.FAQConverter;
import kr.lineus.unistars.dto.FAQ;
import kr.lineus.unistars.dto.FAQImage;
import kr.lineus.unistars.dto.FAQKeyword;
import kr.lineus.unistars.dto.FAQSubject;
import kr.lineus.unistars.dto.LoginRequest;
import kr.lineus.unistars.entity.EventEntity;
import kr.lineus.unistars.entity.FAQEntity;
import kr.lineus.unistars.entity.FAQImageEntity;
import kr.lineus.unistars.entity.FAQKeywordEntity;
import kr.lineus.unistars.exceptionhandler.AppException;
import kr.lineus.unistars.exceptionhandler.AppExceptionCode;
import kr.lineus.unistars.service.FAQService;



@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/faq")
public class FAQController {
	public static final Logger logger = LoggerFactory.getLogger(FAQController.class);
	
	@Autowired
	@Qualifier("faqService")
	FAQService service;

	@PostConstruct
	public void init() {
		service.beforeEveryTest();	
	}	
	
	@GetMapping(value = "/")
	public ResponseEntity<?> loadAll() throws AppException {
		logger.info("Loading all FAQs");
		List<FAQSubject> subjects = FAQConverter.getInstance().faqSubjectEntityToDtoList(service.loadAll());
		return new ResponseEntity<List<FAQSubject>>(subjects, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping(value = "/keyword")
	public ResponseEntity<?> loadAllKeywords() throws AppException {
		logger.info("Loading all Keywords");
		List<FAQKeyword> subjects = FAQConverter.getInstance().faqKeywordEntityToDtoList(service.loadAllKeywords());
		return new ResponseEntity<List<FAQKeyword>>(subjects, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(value = "/saveCategories")
	public ResponseEntity<?> saveSubject(@Valid @RequestBody List<FAQSubject> subjects) throws AppException {
		logger.info("save subject list {}", subjects);
		List<FAQSubject> result = FAQConverter.getInstance().faqSubjectEntityToDtoList(service.saveAllSubjects(FAQConverter.getInstance().faqSubjectDtoToEntityList(subjects)));
		return new ResponseEntity<List<FAQSubject>>(result, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(value = "/")
	public ResponseEntity<?> saveFaq(@Valid @RequestBody FAQ faq, @RequestParam("files") MultipartFile[] files) throws AppException {
		logger.info("saving FAQ {}", faq);
		
		FAQEntity en = FAQConverter.getInstance().faqDtoToEntity(faq);
		faq.getImages().stream().forEach(i ->  {
			FAQImageEntity img = service.getFAQImage(i.getId());
			if(img!=null) {
				en.addImage(img);
			}
		});
		
		Arrays.asList(files)
	                .stream()
	                .forEach(file -> {   
	                	String fileName = StringUtils.cleanPath(file.getOriginalFilename());
	                	try {	                	
	                		FAQImageEntity img = new FAQImageEntity();   
	                		img.setFileName(fileName);
	                		img.setFileType(file.getContentType());
	                		img.setData(file.getBytes());
	                		img.setFaq(en);
	                		en.addImage(img);
	                	} 
	                	catch(IOException ex) {
	                		ex.printStackTrace();
	                	} 
	                });
	    
		FAQEntity result = service.saveFAQ(en);
		
		return new ResponseEntity<FAQEntity>(result, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(value = "/keyword")
	public ResponseEntity<?> saveKeyword(@Valid @RequestBody FAQKeyword keyword){
		logger.info("saving keyword {}", keyword);
		FAQKeywordEntity kwEn = FAQConverter.getInstance().faqKeywordDtoToEntity(keyword);
		kwEn = service.saveFAQkeyword(kwEn);
		return new ResponseEntity<FAQKeyword>(FAQConverter.getInstance().faqKeywordEntityToDto(kwEn), HttpStatus.OK);
		
	}
	
	
	
}
