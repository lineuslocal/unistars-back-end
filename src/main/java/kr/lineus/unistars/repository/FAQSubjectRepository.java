package kr.lineus.unistars.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.lineus.unistars.entity.FAQSubjectEntity;

public interface FAQSubjectRepository extends JpaRepository<FAQSubjectEntity, UUID> {	
}