package kr.lineus.unistars.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.lineus.unistars.entity.FAQKeywordEntity;

@Repository
public interface KeywordRepository extends JpaRepository<FAQKeywordEntity, UUID> {
	public void deleteByIdIn(List<String> ids);
}