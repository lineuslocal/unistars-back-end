package kr.lineus.unistars.service;

import kr.lineus.unistars.dto.User;

public interface UserService {
	
	//check if the user has already registered
	boolean isUserExisting(String userId);
	
	//send verification code
	void sendVerificationCode(String userid);
	
	//save user
	void save(User user);
	
	//log the user in with a password
	boolean authenticate(String userId, String password);
	
	//get all
	//count for health check
	int count();
	
	
	
}
