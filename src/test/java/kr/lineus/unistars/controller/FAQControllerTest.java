package kr.lineus.unistars.controller;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import kr.lineus.unistars.dto.FAQSubject;
import kr.lineus.unistars.service.FAQService;

public class FAQControllerTest extends AbstractTest {

	@Autowired
	FAQService service;
	
	@Before
	public void before() {
		service.beforeEveryTest();
	}

	@After
	public void after() {
		service.afterEveryTest();
	}
	
	@Test
	public void testLoadFaqs_thenOk() throws Exception {
		ResponseEntity<String> response = restTemplate.getForEntity(createURLWithPort("/api/faq/"),String.class);
		Assert.assertEquals(200, response.getStatusCodeValue());
		Assert.assertEquals(true, response.getBody().contains("subject"));
	}
	
	
	
	
	
}
