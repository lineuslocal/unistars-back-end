package kr.lineus.unistars.controller;

import java.io.IOException;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.PutMapping;
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
import kr.lineus.unistars.dto.MessageResponse;
import kr.lineus.unistars.entity.EventEntity;
import kr.lineus.unistars.entity.FAQEntity;
import kr.lineus.unistars.entity.FAQImageEntity;
import kr.lineus.unistars.entity.FAQKeywordEntity;
import kr.lineus.unistars.entity.FAQSubjectEntity;
import kr.lineus.unistars.exceptionhandler.AppException;
import kr.lineus.unistars.exceptionhandler.AppExceptionCode;
import kr.lineus.unistars.service.FAQService;



@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/faq")
public class FAQController {
	public static final Logger logger = LoggerFactory.getLogger(FAQController.class);
	
	@Autowired
	@Qualifier("faqService")
	FAQService service;

	@PostConstruct
	public void init() {
		service.beforeEveryTest();	
	}	
	
	@GetMapping(value = "/list")
	public ResponseEntity<?> loadAll() throws AppException {
		logger.info("Loading all FAQ data");
		List<FAQSubject> subjects = FAQConverter.getInstance().faqSubjectEntityToDtoList(service.loadAll());
		return new ResponseEntity<List<FAQSubject>>(subjects, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(value = "/category")
	public ResponseEntity<?> saveCategory(@Valid @RequestBody List<FAQSubject> subjectList){
		logger.info("saving all FAQ data  {}", subjectList.toString());
		List<FAQSubjectEntity> enList = FAQConverter.getInstance().faqSubjectDtoToEntityList(subjectList);
		enList = service.saveAllSubjects(enList);
		return new ResponseEntity<List<FAQSubject>>(FAQConverter.getInstance().faqSubjectEntityToDtoList(enList), HttpStatus.OK);
		
	}	
	
	@GetMapping(value = "/{faqId}")
	public ResponseEntity<?> get(@RequestParam("faqId") String faqId) throws AppException {
		logger.info("Getting FAQ id = {}" + faqId);
		FAQEntity en = service.getFAQ(faqId);
		return new ResponseEntity<FAQ>(FAQConverter.getInstance().faqEntityToDto(en), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(value = "/")
	public ResponseEntity<?> saveFaq(@Valid @RequestBody FAQ faq, @RequestParam("files") MultipartFile[] files) throws AppException {
		logger.info("saving FAQ {}", faq);
		
		FAQEntity en = FAQConverter.getInstance().faqDtoToEntity(faq);
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
		
		return new ResponseEntity<FAQ>(FAQConverter.getInstance().faqEntityToDto(result), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<?> updateFaq(@Valid @RequestBody FAQ faq, @RequestParam("id") String id, @RequestParam("files") MultipartFile[] files) throws AppException {
		logger.info("saving FAQ {}", faq);
		
		FAQEntity en = service.getFAQ(id);
		if(en==null) {
			throw AppExceptionCode.FAQ_NOTFOUND_400_6000;
		} 
		en.getImages().clear();
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
	                		service.saveFAQImage(img);
	                	} 
	                	catch(IOException ex) {
	                		ex.printStackTrace();
	                	} 
	                });
	    
		FAQEntity result = service.saveFAQ(en);
		
		return new ResponseEntity<FAQ>(FAQConverter.getInstance().faqEntityToDto(result), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping(value = "/keyword/list")
	public ResponseEntity<?> loadAllKeywords() throws AppException {
		logger.info("Loading all Keywords");
		List<FAQKeyword> subjects = FAQConverter.getInstance().faqKeywordEntityToDtoList(service.loadAllKeywords());
		return new ResponseEntity<List<FAQKeyword>>(subjects, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(value = "/keyword")
	public ResponseEntity<?> saveKeyword(@Valid @RequestBody FAQKeyword keyword){
		logger.info("saving keyword {}", keyword);
		FAQKeywordEntity kwEn = FAQConverter.getInstance().faqKeywordDtoToEntity(keyword);
		kwEn = service.saveFAQkeyword(kwEn);
		return new ResponseEntity<FAQKeyword>(FAQConverter.getInstance().faqKeywordEntityToDto(kwEn), HttpStatus.OK);
		
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping(value = "/keyword/{keywordid}")
	public ResponseEntity<?> updateKeyword(@Valid @RequestBody FAQKeyword keyword){
		logger.info("updating keyword {}", keyword);
		FAQKeywordEntity kwEn = FAQConverter.getInstance().faqKeywordDtoToEntity(keyword);
		kwEn = service.saveFAQkeyword(kwEn);
		return new ResponseEntity<FAQKeyword>(FAQConverter.getInstance().faqKeywordEntityToDto(kwEn), HttpStatus.OK);
		
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(value = "/keyword/delete")
	public ResponseEntity<?> deleteKeywords(@Valid @RequestBody List<String> ids){
		logger.info("Deleting keywords {}", String.join(",", ids));
		service.deleteKeywords(ids);
		return ResponseEntity.ok(new MessageResponse("Delete successfully!"));
	}
}
