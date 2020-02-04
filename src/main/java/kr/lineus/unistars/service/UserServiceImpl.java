package kr.lineus.unistars.service;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.lineus.unistars.dto.User;
import kr.lineus.unistars.repository.UserRepository;

@Service
@Transactional
@Qualifier("userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepo;
	
	@Override
	public boolean isUserExisting(String userId) {
		return true;
	}

	@Override
	public void sendVerificationCode(String userid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean authenticate(String userId, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void save(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return 0;
	}

}
