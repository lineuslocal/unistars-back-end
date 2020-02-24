package kr.lineus.unistars.dto;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import kr.lineus.unistars.entity.ApplicantAdditionalInfoAnswerEntity;
import kr.lineus.unistars.entity.ApplicantSurveyAnswerEntity;
import kr.lineus.unistars.entity.EventEntity;
import lombok.Data;

@Data
public class Applicant {

	private String id;
	private Event event;
	private User user;
	private LocalDateTime appliedDate;
	private String state;
	private int numberOfTickets=1;
	private Set<ApplicantAdditionalInfoAnswer> addtionalInfoAnswers = new HashSet<ApplicantAdditionalInfoAnswer>();	
	private Set<ApplicantSurveyAnswer> surveyAnswers = new HashSet<ApplicantSurveyAnswer>();
	
}
