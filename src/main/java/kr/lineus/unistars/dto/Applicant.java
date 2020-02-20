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

import kr.lineus.unistars.entity.ApplicantAdditionalInfoAnswerEntity;
import kr.lineus.unistars.entity.ApplicantSurveyAnswerEntity;
import kr.lineus.unistars.entity.EventEntity;

public class Applicant {

	@Id
	@GeneratedValue
	private String id;
	private String username;
	private String address;
	private String email;
	private String phone;
	private String fullName;
	private LocalDateTime appliedDate;
	private String state;
	private int numberOfTickets=1;
	
	@ManyToOne
	@JoinColumn(name = "event_id", nullable = false, updatable = false)
	private EventEntity event;
	
	@OneToMany(mappedBy = "applicant", cascade =  CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<ApplicantAdditionalInfoAnswerEntity> addtionalInfoAnswers = new HashSet<ApplicantAdditionalInfoAnswerEntity>();
	
	@OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<ApplicantSurveyAnswerEntity> surveyAnswers = new HashSet<ApplicantSurveyAnswerEntity>();
	
}
