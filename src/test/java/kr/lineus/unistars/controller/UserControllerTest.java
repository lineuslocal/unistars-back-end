package kr.lineus.unistars.controller;

import java.net.URI;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import kr.lineus.unistars.dto.User;
import kr.lineus.unistars.service.UserService;
public class UserControllerTest extends AbstractTest {

	@Autowired
	UserService service;
	
	@Before
	public void before() {
		service.beforeEveryTest();
	}

	@After
	public void after() {
		service.afterEveryTest();
	}
	
	@Test
	public void testSendVerificationCode_thenOk() throws Exception {
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> response = restTemplate.postForEntity(createURLWithPort("/api/user/verification/misterhuydo@gmail.com"), entity, String.class);
		Assert.assertEquals(200, response.getStatusCodeValue());
	}
	
	@Test
	public void testSendVerificationCode_whenWrongFormat_thenFailed() throws Exception {
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> response = restTemplate.postForEntity(createURLWithPort("/api/user/verification/misterhuydo"), entity, String.class);
		Assert.assertEquals(400, response.getStatusCodeValue());
	}
	
	@Test
	public void testRegister_whenExists_thenFailed() throws Exception {
		User u = new User();
		u.setEmail("lineus.local@gmail.com");
		HttpEntity<User> entity = new HttpEntity<User>(u, headers);
		ResponseEntity<String> response = restTemplate.postForEntity(createURLWithPort("/api/user/register/9999"), entity, String.class);
		Assert.assertEquals(400, response.getStatusCodeValue());
	}
	
	@Test
	public void testRegister_whenNotExist_thenOk() throws Exception {
		User u = new User();
		u.setUsername("dummy@gmail.com");
		u.setEmail("dummy@gmail.com");
		HttpEntity<User> entity = new HttpEntity<User>(u, headers);
		ResponseEntity<String> response = restTemplate.postForEntity(createURLWithPort("/api/user/register/888888"), entity, String.class);
		Assert.assertEquals(201, response.getStatusCodeValue());
	}
	
	@Test
	public void testLogin_whenUserNameAndPasswordvalid_thenOK() {

		URI uri = UriComponentsBuilder.fromHttpUrl(createURLWithPort("/api/user/authenticate"))
                .queryParam("username", "lineus.local@gmail.com")
                .queryParam("password", "somepwd")
                .build().toUri();
		HttpEntity<?> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> response = restTemplate.postForEntity(uri, entity, String.class);
		Assert.assertEquals(200, response.getStatusCodeValue());
		
	}
	
	@Test
	public void testLogin_whenUserNameAndPasswordInvalid_thenOK() {

		URI uri = UriComponentsBuilder.fromHttpUrl(createURLWithPort("/api/user/authenticate"))
                .queryParam("username", "lineus.local@gmail.com")
                .queryParam("password", "someotherpwd")
                .build().toUri();
		HttpEntity<?> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> response = restTemplate.postForEntity(uri, entity, String.class);
		Assert.assertEquals(400, response.getStatusCodeValue());
		
	}
	
	@Test
	public void testResetPasssword_whenWrongPin_thenFailed() throws Exception {
		URI uri = UriComponentsBuilder.fromHttpUrl(createURLWithPort("/api/user/resetpassword"))
                .queryParam("username", "lineus.local@gmail.com")
                .queryParam("password", "somenewpwd")
                .queryParam("pin", "111111") //wrong pin
                .build().toUri();
		
		
		HttpEntity<?> entity = new HttpEntity<User>(null, headers);
		ResponseEntity<String> response = restTemplate.postForEntity(uri, entity, String.class);
		Assert.assertEquals(400, response.getStatusCodeValue());
	}
	
	@Test
	public void testResetPassword_whenNotExist_thenFailed() throws Exception {
		
		URI uri = UriComponentsBuilder.fromHttpUrl(createURLWithPort("/api/user/resetpassword"))
                .queryParam("username", "stranger@gmail.com")
                .queryParam("password", "somenewpwd")
                .queryParam("pin", "111111") //wrong pin
                .build().toUri();
		
		HttpEntity<?> entity = new HttpEntity<User>(null, headers);
		ResponseEntity<String> response = restTemplate.postForEntity(uri, entity, String.class);
		Assert.assertEquals(400, response.getStatusCodeValue());
	}
	
	@Test
	public void testResetPassword_whenExistsAndPinValid_thenOk() throws Exception {
		URI uri = UriComponentsBuilder.fromHttpUrl(createURLWithPort("/api/user/resetpassword"))
                .queryParam("username", "lineus.local@gmail.com")
                .queryParam("password", "somenewpwd")
                .queryParam("pin", "999999")
                .build().toUri();
		
		HttpEntity<?> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> response = restTemplate.postForEntity(uri, entity, String.class);
		Assert.assertEquals(200, response.getStatusCodeValue());
	}
	

}
