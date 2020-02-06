package kr.lineus.unistars.exceptionhandler;

import org.springframework.http.HttpStatus;


public class AppExceptionCode {

	//USER EXCEPTIONS
	public static AppException USER_ALREADY_REGISTERED_400_4000 = new AppException(HttpStatus.BAD_REQUEST, 4000, "User has already registered.", "", "");
	public static AppException USER_PIN_NOTFOUND_400_4001 = new AppException(HttpStatus.BAD_REQUEST, 4001, "Pin not found.", "", "");
	public static AppException USER_PIN_INVALID_400_4002 = new AppException(HttpStatus.BAD_REQUEST, 4002, "Pin is invalid.", "", "");
	public static AppException USER_LOGIN_FAILED_400_4003 = new AppException(HttpStatus.BAD_REQUEST, 4003, "Login failed.", "", "");
	public static AppException USER_SENDING_VERIFICATION_FAILED_400_4004 = new AppException(HttpStatus.BAD_REQUEST, 4004, "We cannot send a verification code at the moment. Please come back later", "Service provider problem", "");
	public static AppException USER_NOTFOUND_400_4005 = new AppException(HttpStatus.BAD_REQUEST, 4005, "User not found.", "", "");
	
	
	
}
