package kr.lineus.unistars.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.lineus.unistars.entity.FAQKeywordEntity;

public interface KeywordRepository extends JpaRepository<FAQKeywordEntity, UUID> {	
}