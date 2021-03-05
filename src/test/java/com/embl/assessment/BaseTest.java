package com.embl.assessment;


import com.embl.assessment.dto.request.AuthRequest;
import com.embl.assessment.dto.response.AuthResponse;
import com.embl.assessment.dto.response.PersonResponse;
import com.embl.assessment.repository.PersonRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BaseTest  {
	@LocalServerPort
	protected int port;

	@Autowired
	protected TestRestTemplate restTemplate;

	@Autowired
	protected PersonRepository personRepository;

	protected String urlPrefix;

	protected String token;

	protected HttpHeaders headers = new HttpHeaders();

	@BeforeEach
	public void beforeTest() throws Exception {
		if(StringUtils.isBlank(urlPrefix)){
			urlPrefix = "http://localhost:"+port+"/v1/api";
		}
		if(StringUtils.isBlank(token)){
			AuthRequest authRequest = AuthRequest.builder().username("admin").password("admin").build();
			AuthResponse resp = restTemplate.postForObject(urlPrefix+"/auth", authRequest, AuthResponse.class);
			token = resp.getToken();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			headers.set("Authorization", "Bearer "+token);
		}
	}
}
