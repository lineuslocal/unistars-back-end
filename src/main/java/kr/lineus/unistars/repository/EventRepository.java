package kr.lineus.unistars.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.lineus.unistars.entity.EventEntity;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, UUID> {	
	public List<EventEntity> findAllByCategoryId(UUID catId);
	public void deleteByIdIn(List<String> ids);
}