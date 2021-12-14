package org.emartos.beer.catalog.api.core.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HttpClient {

	private static final Logger LOGGER = LoggerFactory.getLogger(HttpClient.class);

	private final RestTemplate restTemplate;

	public HttpClient(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public ResponseEntity<String> get(String url) {
		LOGGER.info(">> get() url {}", url);

		ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), String.class);

		LOGGER.info("<< get() response {}", responseEntity.getBody());
		return responseEntity;
	}

}
