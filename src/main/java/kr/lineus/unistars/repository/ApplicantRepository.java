package kr.lineus.unistars.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.lineus.unistars.entity.ApplicantEntity;

@Repository
public interface ApplicantRepository extends JpaRepository<ApplicantEntity, UUID> {	
	List<ApplicantEntity> findAllByEventId(String eventId);
	List<ApplicantEntity> findAllByUserId(String userId);
}