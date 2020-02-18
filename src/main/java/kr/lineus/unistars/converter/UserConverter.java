package kr.lineus.unistars.converter;

import kr.lineus.unistars.dto.ERole;
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

	private UserConverter() {}

	public UserEntity dtoToEntity(User dto) {
		return map(dto, UserEntity.class);
	}

	public User entityToDto(UserEntity e) {
		User u = map(e, User.class); 
		e.getUserRoles().stream().forEach(i -> {
			if(i.getName() == ERole.ROLE_ADMIN) {
				u.getRoles().add("admin");
			} else if(i.getName() == ERole.ROLE_MODERATOR) {
				u.getRoles().add("mod");
			} else {
				u.getRoles().add("user");
			}
		});
		return u;
	}


}
