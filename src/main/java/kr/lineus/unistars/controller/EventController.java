package kr.lineus.unistars.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import kr.lineus.unistars.converter.ApplicantConverter;
import kr.lineus.unistars.converter.EventConverter;
import kr.lineus.unistars.dto.Applicant;
import kr.lineus.unistars.dto.EEventImageType;
import kr.lineus.unistars.dto.Event;
import kr.lineus.unistars.dto.EventCategory;
import kr.lineus.unistars.dto.LoginRequest;
import kr.lineus.unistars.dto.UploadFileResponse;
import kr.lineus.unistars.entity.ApplicantEntity;
import kr.lineus.unistars.entity.EventEntity;
import kr.lineus.unistars.entity.EventImageEntity;
import kr.lineus.unistars.entity.UserEntity;
import kr.lineus.unistars.exceptionhandler.AppException;
import kr.lineus.unistars.exceptionhandler.AppExceptionCode;
import kr.lineus.unistars.service.EventService;
import kr.lineus.unistars.service.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/event")
public class EventController {
	public static final Logger logger = LoggerFactory.getLogger(EventController.class);

	@Autowired
	@Qualifier("eventService")
	EventService eventService;
	
	@Autowired
	@Qualifier("userService")
	UserService userService;

	@PostConstruct
	public void init() {
		eventService.beforeEveryTest();	
	}	

	@GetMapping(value = "/")
	public ResponseEntity<?> loadAllCategories() throws AppException {
		logger.info("Loading all Event Categories");
		List<EventCategory> subjects = EventConverter.getInstance().eventCatEntityToDtoList(eventService.loadAllCategories());
		return new ResponseEntity<List<EventCategory>>(subjects, HttpStatus.OK);
	}

	@GetMapping(value = "/cat/{cat}")
	public ResponseEntity<?> loadAllEventsByCategory(@PathVariable("cat") String catId) throws AppException {
		logger.info("Loading all Events for cat {}", catId);
		List<Event> subjects = EventConverter.getInstance().eventEntityToDtoList(eventService.loadAllEventsByCategory(catId));
		return new ResponseEntity<List<Event>>(subjects, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/uploadFile/{type}/{eventId}")
	public UploadFileResponse uploadFile(@PathVariable String type, @PathVariable String eventId, @RequestParam("file") MultipartFile file) throws AppException {
		logger.info("Updating image for Event {} - type - {}", eventId, type);
		EventEntity event = eventService.getEvent(eventId);
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
				found = eventService.saveEventImage(i);
			} else {
				found.setEvent(event);
				found.setFileName(fileName);
				found.setFileType(file.getContentType());
				found.setImageType(eType);
				found.setData(file.getBytes());
				eventService.saveEventImage(found);
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
 
	@PreAuthorize("hasRole('ADMIN')")
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
		en = eventService.saveEvent(en);		
		saveImageForEvent(EEventImageType.Profile.name(), profileFile, en);
		saveImageForEvent(EEventImageType.Reg.name(), regFile, en);
		saveImageForEvent(EEventImageType.Guide.name(), guideFile, en);
		saveImageForEvent(EEventImageType.Lecture.name(), lectureFile, en);
		return new ResponseEntity<Event>(EventConverter.getInstance().eventEntityToDto(en), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping(value = "/applicant/{eventId}")
	public ResponseEntity<?> getApplicants(@PathVariable("eventId") String eventId){
		logger.info("Loading applicants for event {}", eventId);
		List<ApplicantEntity> applicantList = eventService.getApplicantsByEventId(eventId);
		return new ResponseEntity<List<Applicant>>(ApplicantConverter.getInstance().applicantEntityToDtoList(applicantList), HttpStatus.OK);
	}
	
	@PostMapping("/applicant/{userid}/{eventId}")
	public ResponseEntity<?> applyEvent(@Valid @RequestBody Applicant request, @PathVariable String userId, @PathVariable String eventId) throws AppException{
		logger.info("Applying event {} for the applicant {}", eventId, request);
		EventEntity event = eventService.getEvent(eventId);
		UserEntity user = userService.findOneById(userId);
		if(user==null) {
			user = userService.find(userId);
		}
		if(event==null) {
			throw AppExceptionCode.EVENT_NOTFOUND_400_5000;		
		} else if(user==null) {
			throw AppExceptionCode.USER_NOTFOUND_400_4005;
		}
		
		ApplicantEntity appEn = ApplicantConverter.getInstance().applicantDtoToEntity(request);
		appEn.setEvent(event);
		appEn.setUser(user);
		appEn.setAppliedDate(LocalDateTime.now());
		
		eventService.saveApplicantEntity(appEn);
		return null;
		
		
		
	}

	

}
