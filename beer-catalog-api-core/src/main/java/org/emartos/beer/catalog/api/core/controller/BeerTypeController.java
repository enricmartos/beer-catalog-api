/**
 * Copyright (c) 2021
 *
 * Author: enricmartos
 */
package org.emartos.beer.catalog.api.core.controller;

import org.emartos.beer.catalog.api.core.exception.BadRequestException;
import org.emartos.beer.catalog.api.core.exception.NotFoundException;
import org.emartos.beer.catalog.api.core.handler.BeerTypeHandler;
import org.emartos.beer.catalog.api.repository.model.BeerTypeDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/beer/type")
public class BeerTypeController {

	private static final Logger LOGGER = LoggerFactory.getLogger(BeerTypeController.class);

	private final BeerTypeHandler beerTypeHandler;

	public BeerTypeController(BeerTypeHandler beerTypeHandler) {
		this.beerTypeHandler = beerTypeHandler;
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public BeerTypeDto createBeerType(@RequestBody BeerTypeDto beerTypeDto) throws BadRequestException {
		LOGGER.info(">> createBeerType() beerTypeDto {}", beerTypeDto);

		BeerTypeDto beerTypeDtoPersisted = beerTypeHandler.createBeerType(beerTypeDto);

		LOGGER.info("<< createBeerType() beerTypeDtoPersisted {}", beerTypeDtoPersisted);
		return beerTypeDtoPersisted;
	}

	@GetMapping(value = "/list", produces = APPLICATION_JSON_VALUE)
	public List<BeerTypeDto> getAllBeerTypes() {
		LOGGER.info(">> getAllBeerTypes()");

		List<BeerTypeDto> beerTypeDtoList = beerTypeHandler.getAllBeerTypes();

		LOGGER.info("<< getAllBeerTypes() beerTypeDtoList {}", beerTypeDtoList);
		return beerTypeDtoList;
	}

	@GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
	public BeerTypeDto getBeerTypeById(@PathVariable Long id) throws NotFoundException {
		LOGGER.info(">> getBeerTypeById() id {}", id);

		BeerTypeDto beerTypeDto = beerTypeHandler.getBeerTypeById(id);

		LOGGER.info("<< getBeerTypeById() beerTypeDto {}", beerTypeDto);
		return beerTypeDto;
	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public BeerTypeDto updateBeerType(@RequestBody BeerTypeDto beerTypeDto) throws NotFoundException, BadRequestException {
		LOGGER.info(">> updateBeerType() beerTypeDto {}", beerTypeDto);

		BeerTypeDto beerTypeDtoPersisted = beerTypeHandler.updateBeerType(beerTypeDto);

		LOGGER.info("<< updateBeerType() beerTypeDtoPersisted {}", beerTypeDtoPersisted);
		return beerTypeDtoPersisted;
	}

	@DeleteMapping(value = "/{id}")
	public boolean deleteBeerTypeById(@PathVariable Long id) throws NotFoundException {
		LOGGER.info(">> deleteBeerTypeById() id {}", id);

		boolean deleted = beerTypeHandler.deleteBeerTypeById(id);

		LOGGER.info("<< deleteBeerTypeById() deleted {}", deleted);
		return deleted;
	}

}
