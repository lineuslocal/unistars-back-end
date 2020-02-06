package kr.lineus.unistars.converter;

import kr.lineus.unistars.dto.User;
import kr.lineus.unistars.entity.UserEntity;


public class UserConverter extends BaseConverter {
	
	public static UserEntity dtoToEntity(User dto) {
		return map(dto, UserEntity.class);
	}
	
	public static User entityToDto(UserEntity e) {
		return map(e, User.class);
	}
	

}
