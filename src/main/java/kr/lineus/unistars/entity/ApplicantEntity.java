package kr.lineus.unistars.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "applicant")
public class ApplicantEntity {
	@Id
	@GeneratedValue
	private UUID id;
	@OneToOne
	@JoinColumn(name = "user_id", nullable = false)
	private UserEntity user;
	private LocalDateTime appliedDate;
	private String state;
	private int numberOfTickets;
	
	@OneToMany(mappedBy = "applicant", cascade =  CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<ApplicantAdditionalInfoAnswerEntity> addtionalInfoAnswers = new HashSet<ApplicantAdditionalInfoAnswerEntity>();
	
	@OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<ApplicantSurveyAnswerEntity> surveyAnswers = new HashSet<ApplicantSurveyAnswerEntity>();
}
