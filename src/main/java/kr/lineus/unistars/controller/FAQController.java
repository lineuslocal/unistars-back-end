package kr.lineus.unistars.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.lineus.unistars.dto.FAQSubject;
import kr.lineus.unistars.exceptionhandler.AppException;
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
		List<FAQSubject> subjects = service.loadAll();
		return new ResponseEntity<List<FAQSubject>>(subjects, HttpStatus.OK);
	}
	
	

	
}
