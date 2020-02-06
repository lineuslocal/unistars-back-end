package kr.lineus.unistars.exceptionhandler;

public class ExceptionConfig {
	

	
	public enum ErrorLevel {
		SHOW_ALL (0),
		SHOW_ALL_BUT_NO_STACKTRACE (1),
		SHOW_NO_DEVELOPER_MESSAGE_AND_EXCEPTION_STACKTRACE (2);
		
		private final int level;
		
		ErrorLevel(int level) {
	      this.level = level;
	    }

		public int getLevel() {
			return level;
		}
		
		public ErrorMessage handleSecurityLevel(ErrorMessage error){
			if(level == 0){ //no constraint
				return error;
			} else if(level == 1){
				if(error.getCode()== 9999){
					error.setDeveloperMessage("");
				}
			} else if(level == 2){
				error.setDeveloperMessage("");
			}
			return error;
		}
		
	}
	
	//SET THE HIGHEST SECURITY AS DEFAULT
	public static ErrorLevel level = ErrorLevel.SHOW_NO_DEVELOPER_MESSAGE_AND_EXCEPTION_STACKTRACE;
	
	public static ErrorMessage handleSecurity(ErrorMessage error, String errorLevel) {
		if(errorLevel.trim().equals("0")){
			level = ErrorLevel.SHOW_ALL;
		} else if (errorLevel.trim().equals("1")){
			level = ErrorLevel.SHOW_ALL_BUT_NO_STACKTRACE;
		} else if (errorLevel.trim().equals("2")){
			level = ErrorLevel.SHOW_NO_DEVELOPER_MESSAGE_AND_EXCEPTION_STACKTRACE;
		}
		return level.handleSecurityLevel(error);
	}
	

}
