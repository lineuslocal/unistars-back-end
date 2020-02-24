package kr.lineus.unistars.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import kr.lineus.unistars.dto.Applicant;
import kr.lineus.unistars.dto.ApplicantAdditionalInfoAnswer;
import kr.lineus.unistars.dto.ApplicantSurveyAnswer;
import kr.lineus.unistars.dto.Event;
import kr.lineus.unistars.dto.EventAdditionalInfo;
import kr.lineus.unistars.dto.EventCategory;
import kr.lineus.unistars.dto.EventCategoryImage;
import kr.lineus.unistars.dto.EventImage;
import kr.lineus.unistars.entity.ApplicantAdditionalInfoAnswerEntity;
import kr.lineus.unistars.entity.ApplicantEntity;
import kr.lineus.unistars.entity.ApplicantSurveyAnswerEntity;
import kr.lineus.unistars.entity.EventAdditionalInfoEntity;
import kr.lineus.unistars.entity.EventCategoryEntity;
import kr.lineus.unistars.entity.EventCategoryImageEntity;
import kr.lineus.unistars.entity.EventEntity;
import kr.lineus.unistars.entity.EventImageEntity;

public class EventApplicantConverter extends BaseConverter {
	
	private static EventApplicantConverter instance;
	
	public static EventApplicantConverter getInstance() {
		if(instance==null) {
			instance = new EventApplicantConverter();
			
			ModelMapper mapper = instance.mapper;
			
			mapper.createTypeMap(ApplicantAdditionalInfoAnswer.class, ApplicantAdditionalInfoAnswerEntity.class).addMappings(map -> {
			    map.using(uuidConverter).map(ApplicantAdditionalInfoAnswer::getId, ApplicantAdditionalInfoAnswerEntity::setId);
			});
			
			mapper.createTypeMap(ApplicantSurveyAnswer.class, ApplicantSurveyAnswerEntity.class).addMappings(map -> {
			    map.using(uuidConverter).map(ApplicantSurveyAnswer::getId, ApplicantSurveyAnswerEntity::setId);
			});
			
			
		}
		return instance;
	}
	
	private EventApplicantConverter() {}

	public ApplicantEntity applicantDtoToEntity(Applicant dto) {	    
		ApplicantEntity en = mapper.map(dto, ApplicantEntity.class);
		en.getSurveyAnswers().stream().forEach(e -> {
			e.setApplicant(en);
		});
		en.getAddtionalInfoAnswers().stream().forEach(e -> {
			e.setApplicant(en);
		});
		return en;
	}
	
	public Applicant applicantEntityToDto(ApplicantEntity e) {
		return map(e, Applicant.class);
	}
	
	public List<ApplicantEntity> eventCatDtoToEntityList(List<Applicant> list){
		return list.stream().map(i -> { return applicantDtoToEntity(i); }).collect(Collectors.toList());	
	}
	
	public List<Applicant> eventCatEntityToDtoList(List<ApplicantEntity> list){
		return list.stream().map(i -> { return applicantEntityToDto(i); }).collect(Collectors.toList());	
	}
}
