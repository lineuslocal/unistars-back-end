package kr.lineus.unistars.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class EventAdditionalInfo {
	private String id;
	@NotBlank
	private String question;
	private boolean required;
}
