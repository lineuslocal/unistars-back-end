package kr.lineus.unistars.converter;

import kr.lineus.unistars.dto.User;
import kr.lineus.unistars.entity.UserEntity;


public class UserConverter extends BaseConverter {
	
	private static UserConverter instance; 
	
	public static UserConverter getInstance() {
		if(instance==null) {
			instance = new UserConverter();
		}
		return instance;
	}
	
	
	public UserEntity dtoToEntity(User dto) {
		return map(dto, UserEntity.class);
	}
	
	public User entityToDto(UserEntity e) {
		return map(e, User.class);
	}
	

}
