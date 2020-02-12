package kr.lineus.unistars.converter;

import java.util.UUID;

import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;

import kr.lineus.unistars.dto.FAQSubject;
import kr.lineus.unistars.entity.FAQSubjectEntity;

public class FAQE extends PropertyMap<FAQSubject, FAQSubjectEntity> {
	protected void configure() {
		Converter<String, UUID> uuidConverter = ctx -> UUID.fromString(ctx.getSource());
		using(uuidConverter).map().setId(UUID.fromString(source.getId()));
	}
}
