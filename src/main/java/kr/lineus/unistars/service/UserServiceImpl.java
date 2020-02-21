package kr.lineus.unistars.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import kr.lineus.unistars.config.MailConfig;
import kr.lineus.unistars.converter.UserConverter;
import kr.lineus.unistars.dto.ELevel;
import kr.lineus.unistars.dto.ERole;
import kr.lineus.unistars.dto.Mail;
import kr.lineus.unistars.dto.UserPin;
import kr.lineus.unistars.dto.User;
import kr.lineus.unistars.entity.UserEntity;
import kr.lineus.unistars.entity.UserRoleEntity;
import kr.lineus.unistars.exceptionhandler.AppException;
import kr.lineus.unistars.exceptionhandler.AppExceptionCode;
import kr.lineus.unistars.repository.RoleRepository;
import kr.lineus.unistars.repository.UserRepository;

@Service
@Transactional
@Qualifier("userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	RoleRepository roleRepo;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	private MailConfig mailConfig;

	@Autowired
	private MailService mailService;
	
	Map<String, UserPin> userIdAndPinMap = new ConcurrentHashMap<String, UserPin>();
	
	@PostConstruct
	public void doPostConstruct() {
		if(roleRepo.count()==0) {
			UserRoleEntity role = new UserRoleEntity();		
			role = new UserRoleEntity();
			role.setName(ERole.ROLE_USER);
			roleRepo.save(role);
			role = new UserRoleEntity();
			role.setName(ERole.ROLE_ADMIN);
			roleRepo.save(role);
			role = new UserRoleEntity();
			role.setName(ERole.ROLE_MODERATOR);
			roleRepo.save(role);
		}
	}

	
	@Override
	public boolean sendVerificationCode(String username) {	
	
		
		User u = new User();
		u.setFullname("New User");
		
		UserEntity en = userRepo.findByUsername(username);
		if(en!=null) {
			u = UserConverter.getInstance().entityToDto(en);
		}
		
		try {
			//try removing old pins
			removeOldPins();
			//reuse pin or create a new one
			UserPin code =null;
			if(userIdAndPinMap.containsKey(username)) {
				code = userIdAndPinMap.get(username);
				code.setAttempts(code.getAttempts()+1);
			} else {
				code = UserPin.getOne();
				userIdAndPinMap.put(username, code);
			}
			// limit to 5 attempts to protect our server from spamming
			if(code.getAttempts()<5) {
				
				//TODO: check if username is an email or a phone number
				//we currently support registration by email
				sendCodeByEmail(username, u.getFullname(), code);
			}			
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}

		
	}

	private void sendCodeByEmail(String email, String fullName, UserPin code) {
		Mail mail = new Mail();
		mail.setMailFrom(mailConfig.EMAIL_SMTP_FROM_ADDRESS);
		mail.setMailTo(email);
		mail.setMailSubject(mailConfig.EMAIL_SUBJECT);

		// TODO: edit the template and add placeholder's values here
		// hard-coded b/c the model of the content is least likely to be changed so
		// often
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("name", fullName);
		model.put("code", code.getPin());
		model.put("location", "Lineus dev team");
		model.put("signature", "https://lineus.net");
		mail.setModel(model);

		mailService.sendEmail(mail);
	}

	@Override
	public UserEntity find(String username, String password) {
		UserEntity u = userRepo.findByUsernameAndPassword(username, password);
		if(u!=null) {
			return userRepo.findByUsernameAndPassword(username, password);
		} else {
			return null;
		}
	}

	@Override
	public long count() {
		return userRepo.count();
	}

	@Override
	public boolean exists(String username) {
		return userRepo.existsUserEntityByUsername(username);
	}

	@Override
	public List<UserEntity> findAll() {
		return userRepo.findAll();
	}

	@Override
	public UserEntity findOneById(String id) {
		Optional<UserEntity> userEntity = userRepo.findById(UUID.fromString(id));
		if(userEntity.isPresent()) {
			return userEntity.get();
		} else {
			return null;
		}
	}

	@Override
	public UserEntity save(User dto) {		
		UserEntity user = UserConverter.getInstance().dtoToEntity(dto);
		user.setUserRoles(getRoles(dto.getRoles()));
		UserEntity uEn = userRepo.save(user);
		return uEn;
	}

	private List<UserRoleEntity> getRoles(List<String> strRoles) {
		List<UserRoleEntity> roles = new ArrayList<UserRoleEntity>();

		if (strRoles == null) {
			UserRoleEntity userRole = roleRepo.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					UserRoleEntity adminRole = roleRepo.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "mod":
					UserRoleEntity modRole = roleRepo.findByName(ERole.ROLE_MODERATOR)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);

					break;
				default:
					UserRoleEntity userRole = roleRepo.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}
		return roles;
	}

	@Override
	public void delete(String id) {
		userRepo.deleteById(UUID.fromString(id));
	}

	@Override
	public void deleteAll() {
		userRepo.deleteAll();
	}

	private void removeOldPins() {
		Predicate<UserPin> isOld = p -> !UserPin.isValid(p);
		userIdAndPinMap.values().removeIf(isOld);
	}

	@Override
	public UserEntity register(User user, String code) throws AppException {
		
		UserPin pin = userIdAndPinMap.get(user.getUsername());
		if(pin!=null) {
			if(pin.isValid() && pin.getPin().equals(code)) {
				userIdAndPinMap.remove(user.getUsername());
				return save(user);
			} else {
				if(!pin.isValid()) {
					userIdAndPinMap.remove(user.getUsername());
				}
				throw AppExceptionCode.USER_PIN_INVALID_400_4002;
			}
			
		} else {
			throw AppExceptionCode.USER_PIN_NOTFOUND_400_4001;
		}
	}

	@Override
	public void beforeEveryTest() {
		
		User dto = new User();
		dto.setUsername("lineus.local@gmail.com");
		dto.setEmail("lineus.local@gmail.com");
		dto.setPassword("somepwd");
		dto.setFullname("User admin");
		dto.setAddress("89 Tran Van Du, Tan Binh District");
		dto.setBirthdate("1/1/1990");
		dto.setCity("HCM");
		dto.setGender("Male");
		dto.setPhonenumber("0907777777");
		dto.setJob("Manager");
		dto.setRoles(Arrays.asList("admin"));
		dto.setLevel(ELevel.Advanced.name());
		save(dto);


		//register some pins in case we are testing the registration process
		userIdAndPinMap.put("lineus.local@gmail.com", new UserPin("999999", System.currentTimeMillis(), 1));
		userIdAndPinMap.put("dummy@gmail.com", new UserPin("888888", System.currentTimeMillis(), 1));
		
	}

	@Override
	public void afterEveryTest() {
		userIdAndPinMap.clear();
		deleteAll();
	}

	@Override
	public UserEntity resetPassword(String username, String code, String password) throws AppException {
		UserPin pin = userIdAndPinMap.get(username);
		if(pin!=null) {
			if(pin.isValid() && pin.getPin().equals(code)) {
				UserEntity u = userRepo.findByUsername(username);
				u.setPassword(password);
				return userRepo.save(u);
			} else {
				if(!pin.isValid()) {
					userIdAndPinMap.remove(username);
				}
				throw AppExceptionCode.USER_PIN_INVALID_400_4002;
			}
			
		} else {
			throw AppExceptionCode.USER_PIN_NOTFOUND_400_4001;
		}
	}

	
	@Override
	public boolean checkPIN(String userId, String code) {
		UserPin pin = userIdAndPinMap.get(userId);
		if(pin!=null) {
			if(pin.isValid() && pin.getPin().equals(code)) {
				return true;
			} else {
				if(!pin.isValid()) {
					userIdAndPinMap.remove(userId);
				}
				return false;
			}
			
		} else {
			return false;
		}
	}


	@Override
	public UserEntity find(String username) {
		return userRepo.findByUsername(username);
	}


	

}
