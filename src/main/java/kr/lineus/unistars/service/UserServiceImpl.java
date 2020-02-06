package kr.lineus.unistars.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

import javax.annotation.PostConstruct;

import org.apache.tomcat.util.bcel.classfile.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import kr.lineus.unistars.config.ConstantValue;
import kr.lineus.unistars.config.MailConfig;
import kr.lineus.unistars.converter.UserConverter;
import kr.lineus.unistars.dto.Mail;
import kr.lineus.unistars.dto.Pin;
import kr.lineus.unistars.dto.User;
import kr.lineus.unistars.entity.UserEntity;
import kr.lineus.unistars.exceptionhandler.AppException;
import kr.lineus.unistars.exceptionhandler.AppExceptionCode;
import kr.lineus.unistars.repository.UserRepository;

@Service
@Transactional
@Qualifier("userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	private MailConfig mailConfig;

	@Autowired
	private MailService mailService;
	
	Map<String, Pin> userIdAndPinMap = new ConcurrentHashMap<String, Pin>();
	
	@Override
	public boolean sendVerificationCode(String username) {	
	
		
		User u = new User();
		u.setFullname("New User");
		
		UserEntity en = userRepo.findByUsername(username);
		if(en!=null) {
			u = UserConverter.entityToDto(en);
		}
		
		try {
			//try removing old pins
			removeOldPins();
			//reuse pin or create a new one
			Pin code =null;
			if(userIdAndPinMap.containsKey(username)) {
				code = userIdAndPinMap.get(username);
				code.setAttempts(code.getAttempts()+1);
			} else {
				code = Pin.getOne();
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

	private void sendCodeByEmail(String email, String fullName, Pin code) {
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
	public User find(String userId, String password) {
		UserEntity u = userRepo.findByUsernameAndPassword(userId, password);
		if(u!=null) {
			return UserConverter.entityToDto(userRepo.findByUsernameAndPassword(userId, password));
		} else {
			return null;
		}
	}

	@Override
	public long count() {
		return userRepo.count();
	}

	@Override
	public boolean exists(String userId) {
		return userRepo.existsUserEntityByUsername(userId);
	}

	@Override
	public List<User> findAll() {
		return UserConverter.mapAll(userRepo.findAll(), User.class);
	}

	@Override
	public User findOneById(String id) {
		Optional<UserEntity> userEntity = userRepo.findById(UUID.fromString(id));
		if(userEntity.isPresent()) {
			return UserConverter.map(userEntity.get(), User.class);
		} else {
			return null;
		}
	}

	@Override
	public User create(User dto) {
		UserEntity uEn = userRepo.save(UserConverter.dtoToEntity(dto));
		return UserConverter.entityToDto(uEn);
	}

	@Override
	public User update(String id, User dto) {
		UserEntity uEn = UserConverter.dtoToEntity(dto);
		uEn.setId(UUID.fromString(id));
		return UserConverter.entityToDto(userRepo.save(UserConverter.dtoToEntity(dto)));
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
		Predicate<Pin> isOld = p -> !Pin.isValid(p);
		userIdAndPinMap.values().removeIf(isOld);
	}

	@Override
	public User register(User user, String code) throws AppException {
		
		Pin pin = userIdAndPinMap.get(user.getUsername());
		if(pin!=null) {
			if(pin.isValid() && pin.getPin().equals(code)) {
				userIdAndPinMap.remove(user.getUsername());
				return create(user);
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
		dto.setLevel(1);
		dto.setPhonenumber("0907777777");
		dto.setJob("Manager");
		dto.setRoles("admin");
		dto.setLevel(1);
		create(dto);


		//register some pins in case we are testing the registration process
		userIdAndPinMap.put("lineus.local@gmail.com", new Pin("999999", System.currentTimeMillis(), 1));
		userIdAndPinMap.put("dummy@gmail.com", new Pin("888888", System.currentTimeMillis(), 1));
		
	}

	@Override
	public void afterEveryTest() {
		userIdAndPinMap.clear();
		deleteAll();
	}

	@Override
	public User resetPassword(String username, String code, String password) throws AppException {
		Pin pin = userIdAndPinMap.get(username);
		if(pin!=null) {
			if(pin.isValid() && pin.getPin().equals(code)) {
				UserEntity u = userRepo.findByUsername(username);
				u.setPassword(password);
				return UserConverter.entityToDto(userRepo.save(u));
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
	

}
