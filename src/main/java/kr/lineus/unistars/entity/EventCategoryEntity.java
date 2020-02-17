package kr.lineus.unistars.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import kr.lineus.unistars.dto.ELevel;

public class EventCategoryEntity {
	@Id
	@GeneratedValue
	private UUID id;
	private String title;
	@Column(length = 50000)
	private String content;
	@Enumerated(EnumType.STRING)
	private ELevel level;
	private boolean status;
}
