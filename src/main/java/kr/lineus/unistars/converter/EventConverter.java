package kr.lineus.unistars.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import kr.lineus.unistars.dto.Event;
import kr.lineus.unistars.dto.EventAdditionalInfo;
import kr.lineus.unistars.dto.EventCategory;
import kr.lineus.unistars.dto.EventCategoryImage;
import kr.lineus.unistars.dto.EventImage;
import kr.lineus.unistars.entity.EventAdditionalInfoEntity;
import kr.lineus.unistars.entity.EventCategoryEntity;
import kr.lineus.unistars.entity.EventCategoryImageEntity;
import kr.lineus.unistars.entity.EventEntity;
import kr.lineus.unistars.entity.EventImageEntity;

public class EventConverter extends BaseConverter {
	
	private static EventConverter instance;
	
	public static EventConverter getInstance() {
		if(instance==null) {
			instance = new EventConverter();
			
			ModelMapper mapper = instance.mapper;
			
			mapper.createTypeMap(Event.class, EventEntity.class).addMappings(map -> {
			    map.using(uuidConverter).map(Event::getId, EventEntity::setId);
			});
			
			mapper.createTypeMap(EventCategory.class, EventCategoryEntity.class).addMappings(map -> {
			    map.using(uuidConverter).map(EventCategory::getId, EventCategoryEntity::setId);
			});
			
			mapper.createTypeMap(EventCategoryImage.class, EventCategoryImageEntity.class).addMappings(map -> {
			    map.using(uuidConverter).map(EventCategoryImage::getId, EventCategoryImageEntity::setId);
			});
			
			mapper.createTypeMap(EventImage.class, EventImageEntity.class).addMappings(map -> {
			    map.using(uuidConverter).map(EventImage::getId, EventImageEntity::setId);
			});
			
			mapper.createTypeMap(EventAdditionalInfo.class, EventAdditionalInfoEntity.class).addMappings(map -> {
			    map.using(uuidConverter).map(EventAdditionalInfo::getId, EventAdditionalInfoEntity::setId);
			});
			
		}
		return instance;
	}
	
	private EventConverter() {}

	public EventCategoryEntity eventCatDtoToEntity(EventCategory dto) {	    
		EventCategoryEntity eventCatEntity = mapper.map(dto, EventCategoryEntity.class);
		eventCatEntity.getEvents().stream().forEach(e -> {
			e.setCategory(eventCatEntity);
		});
		eventCatEntity.getImage().setCategory(eventCatEntity);
		return eventCatEntity;
	}
	
	public EventCategory eventCatEntityToDto(EventCategoryEntity e) {
		return map(e, EventCategory.class);
	}
	
	public List<EventCategoryEntity> eventCatDtoToEntityList(List<EventCategory> list){
		return list.stream().map(i -> { return eventCatDtoToEntity(i); }).collect(Collectors.toList());	
	}
	
	public List<EventCategory> eventCatEntityToDtoList(List<EventCategoryEntity> list){
		return list.stream().map(i -> { return eventCatEntityToDto(i); }).collect(Collectors.toList());	
	}
	

	
	
	public EventEntity eventDtoToEntity(Event dto) {
	    
		EventEntity eventEntity = mapper.map(dto, EventEntity.class);
		eventEntity.getImages().stream().forEach(image -> {
			image.setEvent(eventEntity);
		});
		return eventEntity;
	}
	
	public Event eventEntityToDto(EventEntity e) {
		return map(e, Event.class);
	}
	
	public List<EventEntity> eventDtoToEntityList(List<Event> list){
		return list.stream().map(i -> { return eventDtoToEntity(i); }).collect(Collectors.toList());	
	}
	
	public List<Event> eventEntityToDtoList(List<EventEntity> list){
		return list.stream().map(i -> { return eventEntityToDto(i); }).collect(Collectors.toList());	
	}
	
}
