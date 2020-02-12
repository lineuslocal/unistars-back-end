package kr.lineus.unistars.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class FAQCategory {
	
	private String id;
	private String name;
	
	private List<FAQProduct> products = new ArrayList<FAQProduct>();

}
