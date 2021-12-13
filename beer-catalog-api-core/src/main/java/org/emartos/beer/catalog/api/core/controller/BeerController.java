/**
 * Copyright (c) 2021
 *
 * Author: enricmartos
 */
package org.emartos.beer.catalog.api.core.controller;

import org.emartos.beer.catalog.api.core.exception.BadRequestException;
import org.emartos.beer.catalog.api.core.exception.NotFoundException;
import org.emartos.beer.catalog.api.core.handler.BeerHandler;
import org.emartos.beer.catalog.api.repository.model.BeerDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/beer")
public class BeerController {

	private static final Logger LOGGER = LoggerFactory.getLogger(BeerController.class);

	private final BeerHandler beerHandler;

	public BeerController(BeerHandler beerHandler) {
		this.beerHandler = beerHandler;
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public BeerDto createBeer(@RequestBody BeerDto beerDto) throws BadRequestException {
		LOGGER.info(">> createBeer() beerDto {}", beerDto);

		BeerDto beerDtoPersisted = beerHandler.createBeer(beerDto);

		LOGGER.info("<< createBeer() beerDtoPersisted {}", beerDtoPersisted);
		return beerDtoPersisted;
	}

	@GetMapping(value = "/list", produces = APPLICATION_JSON_VALUE)
	public List<BeerDto> getAllBeers() {
		LOGGER.info(">> getAllBeers()");

		List<BeerDto> beerDtoList = beerHandler.getAllBeers();

		LOGGER.info("<< getAllBeers() beerDtoList {}", beerDtoList);
		return beerDtoList;
	}

	@GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
	public BeerDto getBeerById(@PathVariable Long id) throws NotFoundException {
		LOGGER.info(">> getBeerById() id {}", id);

		BeerDto beerDto = beerHandler.getBeerById(id);

		LOGGER.info("<< getBeerById() beerDto {}", beerDto);
		return beerDto;
	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public BeerDto updateBeer(@RequestBody BeerDto beerDto) throws NotFoundException, BadRequestException {
		LOGGER.info(">> updateBeer() beerDto {}", beerDto);

		BeerDto beerDtoPersisted = beerHandler.updateBeer(beerDto);

		LOGGER.info("<< updateBeer() beerDtoPersisted {}", beerDtoPersisted);
		return beerDtoPersisted;
	}

	@DeleteMapping(value = "/{id}")
	public boolean deleteBeerById(@PathVariable Long id) throws NotFoundException {
		LOGGER.info(">> deleteBeerById() id {}", id);

		boolean deleted = beerHandler.deleteBeerById(id);

		LOGGER.info("<< deleteBeerById() deleted {}", deleted);
		return deleted;
	}

}
