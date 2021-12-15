/**
 * Copyright (c) 2021
 *
 * Author: enricmartos
 */
package org.emartos.beer.catalog.api.core.controller;

import org.emartos.beer.catalog.api.core.dto.PageableResponseDto;
import org.emartos.beer.catalog.api.core.exception.BadRequestException;
import org.emartos.beer.catalog.api.core.exception.BeerCatalogApiException;
import org.emartos.beer.catalog.api.core.exception.NotFoundException;
import org.emartos.beer.catalog.api.core.handler.BeerHandler;
import org.emartos.beer.catalog.api.repository.model.BeerDto;
import org.emartos.beer.catalog.api.repository.model.BeerFilterDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static org.emartos.beer.catalog.api.core.helper.PagingAndSortingHelper.*;
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
	public PageableResponseDto<BeerDto> getAllBeersByParams(
													@RequestParam(defaultValue = DEFAULT_CURRENT_PAGE) Integer currentPage,
													@RequestParam(defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize,
													@RequestParam(defaultValue = DEFAULT_SORTING_PROPERTY_AND_DIRECTION) String[] sort,
													@RequestParam(required = false) String name,
													@RequestParam(required = false) String description,
													@RequestParam(required = false) Float minGraduation,
													@RequestParam(required = false) Float maxGraduation,
													@RequestParam(required = false) Long beerTypeId,
													@RequestParam(required = false) Long manufacturerId) throws BeerCatalogApiException {
		LOGGER.info(">> getAllBeers() page {} size {} sort {}", currentPage, pageSize, sort);

		Pageable pageRequest = getPageableRequest(currentPage, pageSize, sort);
		BeerFilterDto beerFilterDto = BeerFilterDto.builder().name(name).description(description).minGraduation(minGraduation).maxGraduation(maxGraduation)
												   .beerTypeId(beerTypeId).manufacturerId(manufacturerId).build();
		Page<BeerDto> beerDtoPage = beerHandler.getAllBeersByParams(beerFilterDto, pageRequest);
		PageableResponseDto<BeerDto> beerPageableResponseDto = buildPageableResponseDto(beerDtoPage);

		LOGGER.info("<< getAllBeers() beerPageableResponseDto {}", beerPageableResponseDto);
		return beerPageableResponseDto;
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
