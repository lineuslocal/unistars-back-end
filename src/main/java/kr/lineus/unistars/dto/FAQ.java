package kr.lineus.unistars.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class FAQ {
	private String id;
	private String title;
	private String content;
	private Level level;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate createdDate;
	
	private List<Keyword> keywords = new ArrayList<Keyword>();
	
	private List<FAQImage> images = new ArrayList<FAQImage>();
}
