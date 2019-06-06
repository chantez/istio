package chantez.service1.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.http.*;
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

		LOGGER.info("This is a simple service for testing");
		return "this is a simple service for testing";
	}

	@GetMapping("/ping2")
	public String ping2() {

		LOGGER.warn("This is the SECOND simple service for testing2");
		return "PING 2. this is the second simple service for testing2";
	}

	@GetMapping("/ping4")
	public String ping4() {

		LOGGER.error("This is the FOURTH simple service for testing2");
		return "PING 4. this is the fourth simple service for testing2";
	}

	@GetMapping("/health")
	public ResponseEntity<Object> health() {

		LOGGER.info("health endpoint");
		return new ResponseEntity(HttpStatus.OK);
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
