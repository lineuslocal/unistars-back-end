package kr.lineus.unistars.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.lineus.unistars.dto.Event;
import kr.lineus.unistars.entity.EventCategoryEntity;
import kr.lineus.unistars.entity.EventEntity;

public interface EventRepository extends JpaRepository<EventEntity, UUID> {	
	public List<EventEntity> findAllByCategoryId(UUID catId);
}