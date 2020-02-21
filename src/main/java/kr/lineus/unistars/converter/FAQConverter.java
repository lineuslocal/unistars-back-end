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

import kr.lineus.unistars.dto.FAQ;
import kr.lineus.unistars.dto.FAQCategory;
import kr.lineus.unistars.dto.FAQImage;
import kr.lineus.unistars.dto.FAQKeyword;
import kr.lineus.unistars.dto.FAQProduct;
import kr.lineus.unistars.dto.FAQSubject;
import kr.lineus.unistars.entity.FAQCategoryEntity;
import kr.lineus.unistars.entity.FAQEntity;
import kr.lineus.unistars.entity.FAQImageEntity;
import kr.lineus.unistars.entity.FAQKeywordEntity;
import kr.lineus.unistars.entity.FAQProductEntity;
import kr.lineus.unistars.entity.FAQSubjectEntity;

public class FAQConverter extends BaseConverter {
	
	private static FAQConverter instance;
	
	public static FAQConverter getInstance() {
		if(instance==null) {
			instance = new FAQConverter();
			
			ModelMapper mapper = instance.mapper;
			
			mapper.createTypeMap(FAQSubject.class, FAQSubjectEntity.class).addMappings(map -> {
			    map.using(uuidConverter).map(FAQSubject::getId, FAQSubjectEntity::setId);
			});
			
			mapper.createTypeMap(FAQCategory.class, FAQCategoryEntity.class).addMappings(map -> {
			    map.using(uuidConverter).map(FAQCategory::getId, FAQCategoryEntity::setId);
			});
			
			mapper.createTypeMap(FAQProduct.class, FAQProductEntity.class).addMappings(map -> {
			    map.using(uuidConverter).map(FAQProduct::getId, FAQProductEntity::setId);
			});
			
			mapper.createTypeMap(FAQ.class, FAQEntity.class).addMappings(map -> {
			    map.using(uuidConverter).map(FAQ::getId, FAQEntity::setId);
			});
			
			mapper.createTypeMap(FAQImage.class, FAQImageEntity.class).addMappings(map -> {
			    map.using(uuidConverter).map(FAQImage::getId, FAQImageEntity::setId);
			});
			
		}
		return instance;
	}
	
	private FAQConverter() {}
	
	public FAQSubjectEntity faqSubjectDtoToEntity(FAQSubject dto) {
	    
		FAQSubjectEntity subjectEntity = mapper.map(dto, FAQSubjectEntity.class);
		subjectEntity.getCategories().stream().forEach(cat -> 
		{
			
			cat.getProducts().stream().forEach(prod -> {
				prod.getFaqs().stream().forEach(faq ->{
					faq.setProduct(prod);
				});
				prod.setCategory(cat);
			});
			cat.setSubject(subjectEntity);
		});
		return subjectEntity;
	}
	
	public FAQSubject faqSubjectEntityToDto(FAQSubjectEntity e) {
		return map(e, FAQSubject.class);
	}
	
	public List<FAQSubjectEntity> faqSubjectDtoToEntityList(List<FAQSubject> list){
		return list.stream().map(i -> { return faqSubjectDtoToEntity(i); }).collect(Collectors.toList());	
	}
	
	public List<FAQSubject> faqSubjectEntityToDtoList(List<FAQSubjectEntity> list){
		return list.stream().map(i -> { return faqSubjectEntityToDto(i); }).collect(Collectors.toList());	
	}
		
	public FAQKeywordEntity faqKeywordDtoToEntity(FAQKeyword dto) {
	    
		return mapper.map(dto, FAQKeywordEntity.class);
	}
	
	public FAQKeyword faqKeywordEntityToDto(FAQKeywordEntity e) {
		return map(e, FAQKeyword.class);
	}
	
	public List<FAQKeywordEntity> faqKeywordDtoToEntityList(List<FAQKeyword> list){
		return list.stream().map(i -> { return faqKeywordDtoToEntity(i); }).collect(Collectors.toList());	
	}
	
	public List<FAQKeyword> faqKeywordEntityToDtoList(List<FAQKeywordEntity> list){
		return list.stream().map(i -> { return faqKeywordEntityToDto(i); }).collect(Collectors.toList());	
	}
	
	public FAQEntity faqDtoToEntity(FAQ dto) {
	    
		return mapper.map(dto, FAQEntity.class);
	}
	
	public FAQ faqEntityToDto(FAQEntity e) {
		return map(e, FAQ.class);
	}
	
	public List<FAQEntity> faqDtoToEntityList(List<FAQ> list){
		return list.stream().map(i -> { return faqDtoToEntity(i); }).collect(Collectors.toList());	
	}
	
	public List<FAQ> faqEntityToDtoList(List<FAQEntity> list){
		return list.stream().map(i -> { return faqEntityToDto(i); }).collect(Collectors.toList());	
	}
		
}
