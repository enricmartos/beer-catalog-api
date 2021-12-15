/**
 * Copyright (c) 2021
 *
 * Author: enricmartos
 */
package org.emartos.beer.catalog.api.core.controller;

import org.emartos.beer.catalog.api.core.dto.PageableResponseDto;
import org.emartos.beer.catalog.api.core.exception.BadRequestException;
import org.emartos.beer.catalog.api.core.exception.NotFoundException;
import org.emartos.beer.catalog.api.core.handler.ManufacturerHandler;
import org.emartos.beer.catalog.api.repository.model.ManufacturerDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static org.emartos.beer.catalog.api.core.helper.PagingAndSortingHelper.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/manufacturer")
public class ManufacturerController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ManufacturerController.class);

	private final ManufacturerHandler manufacturerHandler;

	public ManufacturerController(ManufacturerHandler manufacturerHandler) {
		this.manufacturerHandler = manufacturerHandler;
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ManufacturerDto createManufacturer(@RequestBody ManufacturerDto manufacturerDto) throws BadRequestException {
		LOGGER.info(">> createManufacturer() manufacturerDto {}", manufacturerDto);

		ManufacturerDto manufacturerDtoPersisted = manufacturerHandler.createManufacturer(manufacturerDto);

		LOGGER.info("<< createManufacturer() manufacturerDtoPersisted {}", manufacturerDtoPersisted);
		return manufacturerDtoPersisted;
	}

	@GetMapping(value = "/list", produces = APPLICATION_JSON_VALUE)
	public PageableResponseDto<ManufacturerDto> getAllManufacturers(@RequestParam(defaultValue = DEFAULT_CURRENT_PAGE) Integer currentPage,
													 @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize,
													 @RequestParam(defaultValue = DEFAULT_SORTING_PROPERTY_AND_DIRECTION) String[] sort) throws BadRequestException {
		LOGGER.info(">> getAllManufacturers()");

		Pageable pageRequest = getPageableRequest(currentPage, pageSize, sort);
		Page<ManufacturerDto> manufacturerDtoPage = manufacturerHandler.getAllManufacturers(pageRequest);
		PageableResponseDto<ManufacturerDto> manufacturerPageableResponseDto = buildPageableResponseDto(manufacturerDtoPage);

		LOGGER.info("<< getAllManufacturers() manufacturerPageableResponseDto {}", manufacturerPageableResponseDto);
		return manufacturerPageableResponseDto;
	}

	@GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
	public ManufacturerDto getManufacturerById(@PathVariable Long id) throws NotFoundException {
		LOGGER.info(">> getManufacturerById() id {}", id);

		ManufacturerDto manufacturerDto = manufacturerHandler.getManufacturerById(id);

		LOGGER.info("<< getManufacturerById() manufacturerDto {}", manufacturerDto);
		return manufacturerDto;
	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ManufacturerDto updateManufacturer(@RequestBody ManufacturerDto manufacturerDto) throws NotFoundException, BadRequestException {
		LOGGER.info(">> updateManufacturer() manufacturerDto {}", manufacturerDto);

		ManufacturerDto manufacturerDtoPersisted = manufacturerHandler.updateManufacturer(manufacturerDto);

		LOGGER.info("<< updateManufacturer() manufacturerDtoPersisted {}", manufacturerDtoPersisted);
		return manufacturerDtoPersisted;
	}

	@DeleteMapping(value = "/{id}")
	public boolean deleteManufacturerById(@PathVariable Long id) throws NotFoundException {
		LOGGER.info(">> deleteManufacturerById() id {}", id);

		boolean deleted = manufacturerHandler.deleteManufacturerById(id);

		LOGGER.info("<< deleteManufacturerById() deleted {}", deleted);
		return deleted;
	}

}
