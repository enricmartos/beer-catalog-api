/**
 * Copyright (c) 2021
 *
 * Author: enricmartos
 */
package org.emartos.beer.catalog.api.core.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SampleController.class);

	@GetMapping(value = "/hello", produces = MediaType.TEXT_PLAIN_VALUE)
	public String hello() {
		LOGGER.info(">> hello()");
		LOGGER.info("<< hello()");
		return "Hello World!";
	}
}
