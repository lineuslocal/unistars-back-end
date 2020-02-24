package kr.lineus.unistars.exceptionhandler;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import kr.lineus.unistars.config.GeneralConfig;


@ControllerAdvice
public class SpringGlobalExceptionHandler {

	@Autowired
    GeneralConfig config;
	 
	@ExceptionHandler(AppException.class)
	public ResponseEntity handleAppException(AppException ex) {
		return new ResponseEntity<String>(ExceptionConfig.handleSecurity(new ErrorMessage(ex), config.ERROR_LEVEL).toString(), ex.getStatus());
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity handle(NoHandlerFoundException ex){
		return new ResponseEntity<String>(ExceptionConfig.handleSecurity(new ErrorMessage(ex), config.ERROR_LEVEL).toString(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(Throwable.class)
	public ResponseEntity handle(Throwable ex){
		return toResponse(ex);
	}

	private ResponseEntity toResponse(Throwable ex) {

		ErrorMessage errorMessage = new ErrorMessage();		
		errorMessage.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value()); 
		errorMessage.setCode(9999);
		errorMessage.setMessage(ex.getMessage());
		StringWriter errorStackTrace = new StringWriter();
		ex.printStackTrace(new PrintWriter(errorStackTrace));
		errorMessage.setDeveloperMessage(errorStackTrace.toString());
		errorMessage.setLink("");
		errorMessage = ExceptionConfig.handleSecurity(errorMessage, config.ERROR_LEVEL);
		return new ResponseEntity<String>(errorMessage.toString(), HttpStatus.NOT_FOUND);

	}


}
