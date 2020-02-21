package kr.lineus.unistars.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class FAQSubject {
	
	private String id;	
	private String name;
	private String krName;
	private String note;
	
	List<FAQCategory> categories = new ArrayList<FAQCategory>();
}
