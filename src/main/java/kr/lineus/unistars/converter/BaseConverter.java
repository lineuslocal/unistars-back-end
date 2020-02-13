package kr.lineus.unistars.converter;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.modelmapper.spi.SourceGetter;

import kr.lineus.unistars.dto.FAQSubject;
import kr.lineus.unistars.entity.FAQSubjectEntity;

public class BaseConverter {
	
	public static Converter<String, UUID> uuidConverter = ctx -> ctx.getSource()!=null? UUID.fromString(ctx.getSource()) : null;
	protected ModelMapper mapper= new ModelMapper();

	/*
	public static ModelMapper getMapper() {
		
		Converter<String, UUID> uuidConverter = ctx -> UUID.fromString(ctx.getSource());
		TypeMap<FAQSubject, FAQSubjectEntity> typeMap = mapper.createTypeMap(FAQSubject.class, FAQSubjectEntity.class);
		typeMap.addMappings(map -> {
		    map.using(uuidConverter).map(FAQSubject::getId, FAQSubjectEntity::setId);
		});
		
		
		//mapper.createTypeMap(S, D);
		return mapper;
	}*/
	
	public <T> T map(Object src, Class<T> className) {
//		ModelMapper mapper= new ModelMapper();
//		TypeMap<?, ?> typeMap = mapper.createTypeMap(src.getClass(), className);
//		typeMap.addMappings(map -> {
//		
//		});
		
		return mapId(src, mapper.map(src, className));
		
	}
	
	@SuppressWarnings("unchecked")
	protected <T> T mapId(Object src, Object dest) {
		try {
			String methodName = "getId";
			Method getIdMethod = src.getClass().getMethod(methodName);
			Object value = getIdMethod.invoke(src);
			methodName = "setId";
			if(value!=null) {
				if(value instanceof UUID) {       	
					Method setIdMethod = dest.getClass().getMethod(methodName, String.class);
					setIdMethod.invoke(dest, ((UUID) value).toString());
				} else if(value instanceof String){
					if(!((String) value).isEmpty()) {
						Method setIdMethod = dest.getClass().getMethod(methodName, UUID.class);
						setIdMethod.invoke(dest, UUID.fromString(((String) value)));
					}
				}	
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return (T) dest;
	}
	
	public <D, T> List<D> mapAll(final Collection<T> entityList, Class<D> outCLass) {
		return entityList.stream()
				.map(entity -> map(entity, outCLass))
				.collect(Collectors.toList());
	}


}
