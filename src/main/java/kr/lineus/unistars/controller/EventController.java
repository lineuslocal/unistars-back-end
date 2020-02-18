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

import kr.lineus.unistars.dto.EventCategory;
import kr.lineus.unistars.exceptionhandler.AppException;
import kr.lineus.unistars.service.EventService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/event")
public class EventController {
public static final Logger logger = LoggerFactory.getLogger(EventController.class);
	
	@Autowired
	@Qualifier("eventService")
	EventService service;

	@PostConstruct
	public void init() {
		service.beforeEveryTest();	
	}	
	
	@GetMapping(value = "/")
	public ResponseEntity<?> loadAllCategories() throws AppException {
		logger.info("Loading all Events");
		List<EventCategory> subjects = service.loadAllCategories();
		return new ResponseEntity<List<EventCategory>>(subjects, HttpStatus.OK);
	}
	
	
}
