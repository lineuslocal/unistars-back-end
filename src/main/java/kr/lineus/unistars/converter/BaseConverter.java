package kr.lineus.unistars.converter;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

public class BaseConverter {
	
	protected static ModelMapper mapper= new ModelMapper();

	public static <T> T map(Object src, Class<T> className) {
		
		return mapId(src, mapper.map(src, className));
		
	}
	
	@SuppressWarnings("unchecked")
	protected static <T> T mapId(Object src, Object dest) {
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
	
	public static <D, T> List<D> mapAll(final Collection<T> entityList, Class<D> outCLass) {
		return entityList.stream()
				.map(entity -> map(entity, outCLass))
				.collect(Collectors.toList());
	}


}
