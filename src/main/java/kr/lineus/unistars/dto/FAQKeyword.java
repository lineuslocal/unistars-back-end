package kr.lineus.unistars.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class FAQKeyword {

    private String id;
    private String keyword;
	private String note;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate createdDate = LocalDate.now();	
    
}
