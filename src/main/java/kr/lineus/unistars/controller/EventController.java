package kr.lineus.unistars.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import kr.lineus.unistars.converter.EventConverter;
import kr.lineus.unistars.dto.EEventImageType;
import kr.lineus.unistars.dto.Event;
import kr.lineus.unistars.dto.EventCategory;
import kr.lineus.unistars.dto.LoginRequest;
import kr.lineus.unistars.dto.UploadFileResponse;
import kr.lineus.unistars.entity.ApplicantEntity;
import kr.lineus.unistars.entity.EventEntity;
import kr.lineus.unistars.entity.EventImageEntity;
import kr.lineus.unistars.exceptionhandler.AppException;
import kr.lineus.unistars.exceptionhandler.AppExceptionCode;
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
		logger.info("Loading all Event Categories");
		List<EventCategory> subjects = service.loadAllCategories();
		return new ResponseEntity<List<EventCategory>>(subjects, HttpStatus.OK);
	}

	@GetMapping(value = "/cat/{cat}")
	public ResponseEntity<?> loadAllEventsByCategory(@PathVariable("cat") String catId) throws AppException {
		logger.info("Loading all Events for cat {}", catId);
		List<Event> subjects = service.loadAllEventsByCategory(catId);
		return new ResponseEntity<List<Event>>(subjects, HttpStatus.OK);
	}

	@PostMapping("/uploadFile/{type}/{eventId}")
	public UploadFileResponse uploadFile(@PathVariable String type, @PathVariable String eventId, @RequestParam("file") MultipartFile file) throws AppException {
		logger.info("Updating image for Event {} - type - {}", eventId, type);
		EventEntity event = service.getEvent(eventId);
		if(event!=null) {
			return saveImageForEvent(type, file, event);
		} else {
			throw AppExceptionCode.EVENT_NOTFOUND_400_5000;
		}			
	}

	private UploadFileResponse saveImageForEvent(String type, MultipartFile file, EventEntity event)
			throws AppException {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		if(fileName.contains("..")) {
			throw AppExceptionCode.COMMON_FILE_INVALIDPATH_400_9000.addMessageParams(fileName);
		}
		try {
			EEventImageType eType = EEventImageType.valueOf(type);

			EventImageEntity found = event.getImages().stream().filter(i -> i.getImageType() == eType).findFirst().get();
			if(found==null) {
				EventImageEntity i = new EventImageEntity();
				i.setEvent(event);
				i.setFileName(fileName);
				i.setFileType(file.getContentType());
				i.setImageType(eType);
				i.setData(file.getBytes());
				found = service.saveEventImage(i);
			} else {
				found.setEvent(event);
				found.setFileName(fileName);
				found.setFileType(file.getContentType());
				found.setImageType(eType);
				found.setData(file.getBytes());
				service.saveEventImage(found);
			}

			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
					.path("/downloadFile/")
					.path(found.getId().toString())
					.toUriString();

			logger.info("UploadFileResponse(dbFile.getFileName({}), fileDownloadUri:{}, file.getContentType({}), file.getSize({})",
					found.getFileName(), fileDownloadUri, file.getContentType(), file.getSize());
			
			return new UploadFileResponse(found.getFileName(), fileDownloadUri, file.getContentType(), file.getSize()); 
		
		} catch(IOException ex) {
			throw AppExceptionCode.COMMON_FILE_IOEXCEPTION_500_9000.addMessageParams(fileName);
		} catch(IllegalArgumentException ex) {
			throw AppExceptionCode.EVENT_IMAGETYPENOTFOUND_400_5001;
		}
	}
 
	@PostMapping("/")
	public ResponseEntity<?> saveEvent(@Valid @RequestBody Event eventRequest, 
			@RequestParam("profile_file") MultipartFile profileFile, 
			@RequestParam("reg_file") MultipartFile regFile, 
			@RequestParam("guide_file") MultipartFile guideFile, 
			@RequestParam("lecture_file") MultipartFile lectureFile) throws AppException{
		
		logger.info("Saving image for Event {}", eventRequest.toString());
		
		//fix: don't count on this list, it is just for reading not saving
		eventRequest.getImages().clear();
		EventEntity en = EventConverter.getInstance().eventDtoToEntity(eventRequest);
		en = service.saveEvent(en);		
		saveImageForEvent(EEventImageType.Profile.name(), profileFile, en);
		saveImageForEvent(EEventImageType.Reg.name(), regFile, en);
		saveImageForEvent(EEventImageType.Guide.name(), guideFile, en);
		saveImageForEvent(EEventImageType.Lecture.name(), lectureFile, en);
		return new ResponseEntity<Event>(EventConverter.getInstance().eventEntityToDto(en), HttpStatus.OK);
	}
	
	@GetMapping(value = "/applicant/{eventId}")
	public ResponseEntity<?> getApplicants(@PathVariable("eventId") String eventId){
		logger.info("Loading applicants for event {}", eventId);
		return null;
		
	}
}
