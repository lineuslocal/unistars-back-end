package kr.lineus.unistars.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import kr.lineus.unistars.dto.Applicant;
import kr.lineus.unistars.dto.UserImage;
import kr.lineus.unistars.entity.ApplicantEntity;

public class ApplicantConverter extends BaseConverter {
	
	private static ApplicantConverter instance;
	
	public static ApplicantConverter getInstance() {
		if(instance==null) {
			instance = new ApplicantConverter();
			
			ModelMapper mapper = instance.mapper;
			
			mapper.createTypeMap(Applicant.class, ApplicantEntity.class).addMappings(map -> {
			    map.using(uuidConverter).map(Applicant::getId, ApplicantEntity::setId);
			});
		}
		return instance;
	}
	
	private ApplicantConverter() {}

	public ApplicantEntity applicantDtoToEntity(Applicant dto) {	    
		ApplicantEntity applicantEntity = mapper.map(dto, ApplicantEntity.class);
		return applicantEntity;
	}
	
	public Applicant applicantEntityToDto(ApplicantEntity e) {
		Applicant app =  map(e, Applicant.class);
		return app;
	}
	
	public List<ApplicantEntity> applicantDtoToEntityList(List<Applicant> list){
		return list.stream().map(i -> { return applicantDtoToEntity(i); }).collect(Collectors.toList());	
	}
	
	public List<Applicant> applicantEntityToDtoList(List<ApplicantEntity> list){
		return list.stream().map(i -> { return applicantEntityToDto(i); }).collect(Collectors.toList());	
	}
	
	
	
}
