package kr.lineus.unistars.entity;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "applicant_additionalinfo_answer")
public class ApplicantAdditionalInfoAnswerEntity {
	@Id
	@GeneratedValue
	private UUID id;
	@ManyToOne
	@JoinColumn(name = "applicant_id", nullable = false)
	private ApplicantEntity applicant;
	private String question;
	private String answer;
}
