package kr.lineus.unistars.service;

import kr.lineus.unistars.dto.User;
import kr.lineus.unistars.entity.UserEntity;
import kr.lineus.unistars.exceptionhandler.AppException;

public interface UserService extends CRUDOperationService<UserEntity, User>, ControllerTestingService {
	
	//check if the user has already registered
	boolean exists(String username);
	
	//send verification code
	boolean sendVerificationCode(String username);
	
	//log the user in with a password
	UserEntity find(String username, String password);
	
	UserEntity find(String username);
	
	UserEntity register(User user, String pin) throws AppException;

	UserEntity resetPassword(String username, String pin, String password) throws AppException;
	
	boolean checkPIN(String username, String pin) throws AppException;
	
	
}
