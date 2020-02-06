package kr.lineus.unistars.dto;

import java.security.SecureRandom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data    
@NoArgsConstructor
@AllArgsConstructor
public class Pin {
	String pin;
	long issueTime;
	int attempts;
	public static long aliveTime = 1*60*60*1000;
	
	
	public static Pin getOne() {
		
		SecureRandom random = new SecureRandom();
		int num = random.nextInt(100000);
		String code = String.format("%06d", num);
		return new Pin(code, System.currentTimeMillis(), 1);
	}
	
	public boolean isValid() {
		return isValid(this);
	}
	
	public static boolean isValid(Pin p) {
		return System.currentTimeMillis() - p.getIssueTime() < aliveTime;
	}
}
