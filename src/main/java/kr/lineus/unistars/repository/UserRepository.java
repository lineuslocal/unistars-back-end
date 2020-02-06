package kr.lineus.unistars.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.lineus.unistars.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

	boolean existsUserEntityByUsername(String username);
	
	UserEntity findByUsernameAndPassword(String username, String password);
	
	UserEntity findByUsername(String username);
	
}