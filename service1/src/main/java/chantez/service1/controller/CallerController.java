package chantez.service1.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/caller")
public class CallerController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CallerController.class);

	@Autowired
	BuildProperties buildProperties;
	@Autowired
	RestTemplate restTemplate;

	@GetMapping("/ping")
	public String ping() {
		
		return "this is a simple service for test istio";
	}

	@GetMapping("/ping2")
	public String ping2() {

		return "PING 3. this is the second simple service for test istio";
	}

	@GetMapping("/ping3")
	public String ping3() {

		RestTemplate restTemplate = new RestTemplate();
		String fooResourceUrl
				= "http://helloworld:5000/hello";
		ResponseEntity<String> response
				= restTemplate.getForEntity(fooResourceUrl, String.class);

		return response.getBody();
	}
}
