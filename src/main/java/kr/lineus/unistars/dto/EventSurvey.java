package kr.lineus.unistars.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class EventSurvey {
	private String id;
	@NotBlank
	private String question;
	private String selections;
}
