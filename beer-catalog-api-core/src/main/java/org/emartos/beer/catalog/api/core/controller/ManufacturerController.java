/**
 * Copyright (c) 2021
 *
 * Author: enricmartos
 */
package org.emartos.beer.catalog.api.core.controller;

import org.emartos.beer.catalog.api.core.service.ManufacturerService;
import org.emartos.beer.catalog.api.repository.model.ManufacturerDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/manufacturer")
public class ManufacturerController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ManufacturerController.class);

	private final ManufacturerService manufacturerService;

	public ManufacturerController(ManufacturerService manufacturerService) {
		this.manufacturerService = manufacturerService;
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ManufacturerDto createManufacturer(@RequestBody ManufacturerDto manufacturerDto) {
		LOGGER.info(">> createManufacturer() manufacturerDto {}", manufacturerDto);

		ManufacturerDto manufacturerDtoPersisted = manufacturerService.createManufacturer(manufacturerDto);

		LOGGER.info("<< createManufacturer() manufacturerDtoPersisted {}", manufacturerDtoPersisted);
		return manufacturerDtoPersisted;
	}

	@GetMapping(value = "/list", produces = APPLICATION_JSON_VALUE)
	public List<ManufacturerDto> getAllManufacturers() {
		LOGGER.info(">> getAllManufacturers()");

		List<ManufacturerDto> manufacturerDtoList = manufacturerService.getAllManufacturers();

		LOGGER.info("<< getAllManufacturers() manufacturerDtoList {}", manufacturerDtoList);
		return manufacturerDtoList;
	}

	@GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
	public ManufacturerDto getManufacturerById(@PathVariable Long id) {
		LOGGER.info(">> getManufacturerById() id {}", id);

		ManufacturerDto manufacturerDto = manufacturerService.getManufacturerById(id);

		LOGGER.info("<< getManufacturerById() manufacturerDto {}", manufacturerDto);
		return manufacturerDto;
	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ManufacturerDto updateManufacturer(@RequestBody ManufacturerDto manufacturerDto) {
		LOGGER.info(">> updateManufacturer() manufacturerDto {}", manufacturerDto);

		ManufacturerDto manufacturerDtoPersisted = manufacturerService.updateManufacturer(manufacturerDto);

		LOGGER.info("<< updateManufacturer() manufacturerDtoPersisted {}", manufacturerDtoPersisted);
		return manufacturerDtoPersisted;
	}

	@DeleteMapping(value = "/{id}")
	public boolean deleteManufacturerById(@PathVariable Long id) {
		LOGGER.info(">> deleteManufacturerById() id {}", id);

		boolean deleted = manufacturerService.deleteManufacturerById(id);

		LOGGER.info("<< deleteManufacturerById() deleted {}", deleted);
		return deleted;
	}

}
