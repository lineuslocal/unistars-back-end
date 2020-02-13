package kr.lineus.unistars.controller;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.util.UriComponentsBuilder;

import kr.lineus.unistars.dto.User;
import kr.lineus.unistars.exceptionhandler.AppException;
import kr.lineus.unistars.exceptionhandler.AppExceptionCode;
import kr.lineus.unistars.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

	public static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	@Qualifier("userService")
	UserService service;

	@PostConstruct
	public void init() {
		
		
	}	
	
	@RequestMapping(value = "/register/{pin}", method = RequestMethod.POST)
	public ResponseEntity<?> register(@PathVariable("pin") String pin, @RequestBody User user, UriComponentsBuilder ucBuilder) throws AppException {
		logger.info("Creating user : {}", user);
		if(!service.exists(user.getEmail())) {
			User result = service.register(user, pin);
			return new ResponseEntity<User>(result, HttpStatus.CREATED);
		} else {
			throw AppExceptionCode.USER_ALREADY_REGISTERED_400_4000;
		}
	}
	
	@RequestMapping(value = "/verification/{userId}", method = RequestMethod.POST)
	public DeferredResult<ResponseEntity<?>> sendVerificationCode(@PathVariable("userId") String userId) throws AppException {
		logger.info("Request verification code for userId : {}", userId );
		DeferredResult<ResponseEntity<?>> result = new DeferredResult<>();
		new Thread(() -> {

			if(service.sendVerificationCode(userId)) {
				result.setResult(new ResponseEntity(HttpStatus.OK));
			} else {
				result.setErrorResult(AppExceptionCode.USER_SENDING_VERIFICATION_FAILED_400_4004);
			}
    		
    	}).start();
		
		return result;
		
	}
		
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) throws AppException {
		logger.info("Authenticating user : {}", username);
		User u = service.find(username, password);
		if(u!=null) {
			return new ResponseEntity<User>(u, HttpStatus.OK);
		} else {
			throw AppExceptionCode.USER_LOGIN_FAILED_400_4003;
		}
	}
	
	@RequestMapping(value = "/resetpassword", method = RequestMethod.POST)
	public ResponseEntity<?> resetPassword(@RequestParam String pin,  @RequestParam String username, @RequestParam String password) throws AppException {
		logger.info("Reset password for : {}", username);
		
		if(service.exists(username)) {
			User result = service.resetPassword(username, pin, password);
			return new ResponseEntity<User>(result, HttpStatus.OK);
		} else {
			throw AppExceptionCode.USER_NOTFOUND_400_4005;
		}
	}
	
	

}
