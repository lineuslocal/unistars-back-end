package kr.lineus.unistars.dto;

import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class FAQ {
	private String id;
	private String title;
	private String content;
	
	@Enumerated(EnumType.STRING)
	private Level level;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date created_date;	
}
