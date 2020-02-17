package kr.lineus.unistars.service;

import kr.lineus.unistars.dto.User;
import kr.lineus.unistars.exceptionhandler.AppException;

public interface UserService extends CRUDOperationService<User>, ControllerTestingService {
	
	//check if the user has already registered
	boolean exists(String userId);
	
	//send verification code
	boolean sendVerificationCode(String userid);
	
	//log the user in with a password
	User find(String userId, String password);
	
	User register(User user, String pin) throws AppException;

	User resetPassword(String username, String pin, String password) throws AppException;
	
	
	
}
