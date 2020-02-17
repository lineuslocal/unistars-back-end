package kr.lineus.unistars.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.lineus.unistars.dto.ERole;
import kr.lineus.unistars.entity.UserRoleEntity;

@Repository
public interface RoleRepository extends JpaRepository<UserRoleEntity, Long> {
	Optional<UserRoleEntity> findByName(ERole name);
}
