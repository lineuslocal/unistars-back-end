package kr.lineus.unistars.converter;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.DestinationSetter;
import org.modelmapper.spi.SourceGetter;
import org.springframework.beans.factory.annotation.Autowired;

import kr.lineus.unistars.dto.FAQCategory;
import kr.lineus.unistars.dto.FAQProduct;
import kr.lineus.unistars.dto.FAQSubject;
import kr.lineus.unistars.entity.FAQCategoryEntity;
import kr.lineus.unistars.entity.FAQProductEntity;
import kr.lineus.unistars.entity.FAQSubjectEntity;

public class FAQConverter extends BaseConverter{
	
	//static ModelMapper mapper =new ModelMapper();
	public static FAQSubjectEntity faqSubjectDtoToEntity(FAQSubject dto) {
//		
//		TypeMap<FAQSubject, FAQSubjectEntity> typeMap = mapper.createTypeMap(FAQSubject.class, FAQSubjectEntity.class);
//		Converter<String, UUID> uuidConverter = ctx -> UUID.fromString(ctx.getSource());
//		typeMap.addMappings(map -> {
//			map.using(ct -> ct.getSource());
//		    map.using(uuidConverter).map(FAQSubject::getId, FAQSubjectEntity::setId);
//		});
//		mapper.getConfiguration()
//         .setMatchingStrategy(MatchingStrategies.STRICT);
//		return mapper.map(dto, FAQSubjectEntity.class);
		
		
		return mapper.map(dto, FAQSubjectEntity.class);
	}
	
	public static FAQSubject faqSubjectEntityToDto(FAQSubjectEntity e) {
		return map(e, FAQSubject.class);
	}
	
	public static List<FAQSubjectEntity> faqSubjectDtoToEntityList(List<FAQSubject> list){
		return list.stream().map(i -> { return faqSubjectDtoToEntity(i); }).collect(Collectors.toList());	
	}
	
	public static List<FAQSubject> faqSubjectEntityToDtoList(List<FAQSubjectEntity> list){
		return list.stream().map(i -> { return faqSubjectEntityToDto(i); }).collect(Collectors.toList());	
	}
	
}
