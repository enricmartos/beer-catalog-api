package org.emartos.beer.catalog.api.core.handler.impl;

import org.emartos.beer.catalog.api.core.exception.BadRequestException;
import org.emartos.beer.catalog.api.core.exception.NotFoundException;
import org.emartos.beer.catalog.api.core.handler.BeerHandler;
import org.emartos.beer.catalog.api.core.helper.BeerValidationHelper;
import org.emartos.beer.catalog.api.core.service.BeerService;
import org.emartos.beer.catalog.api.repository.model.BeerDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BeerHandlerImpl implements BeerHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(BeerHandlerImpl.class);

	private final BeerService beerService;

	public BeerHandlerImpl(BeerService beerService) {
		this.beerService = beerService;
	}

	@Override
	public BeerDto createBeer(BeerDto beerDto) throws BadRequestException {
		LOGGER.debug(">> createBeer() beerDto {}", beerDto);

		validateBeer(beerDto);
		BeerDto beerDtoPersisted = beerService.createBeer(beerDto);

		LOGGER.debug("<< createBeer() BeerDtoPersisted {}", beerDtoPersisted);
		return beerDtoPersisted;
	}

	@Override
	public List<BeerDto> getAllBeers() {
		LOGGER.debug(">> getAllBeers()");

		List<BeerDto> beerDtoList = beerService.getAllBeers();

		LOGGER.debug("<< getAllBeers() beerDtoList {}", beerDtoList);
		return beerDtoList;
	}

	@Override
	public BeerDto getBeerById(Long id) throws NotFoundException {
		LOGGER.debug(">> getBeerById() id {}", id);

		BeerDto beerDto = beerService.getBeerById(id);

		LOGGER.debug("<< getBeerById() beerDto {}", beerDto);
		return beerDto;
	}

	@Override
	public BeerDto updateBeer(BeerDto beerDto) throws NotFoundException, BadRequestException {
		LOGGER.debug(">> updateBeer() beerDto {}", beerDto);

		validateBeer(beerDto);
		BeerDto beerDtoPersisted = beerService.updateBeer(beerDto);

		LOGGER.debug("<< updateBeer() BeerDtoPersisted {}", beerDtoPersisted);
		return beerDtoPersisted;
	}

	@Override
	public boolean deleteBeerById(Long id) throws NotFoundException {
		LOGGER.debug(">> deleteBeerById() id {}", id);

		boolean deleted = beerService.deleteBeerById(id);

		LOGGER.debug("<< deleteBeerById() deleted {}", deleted);
		return deleted;
	}

	// region Private methods

	private void validateBeer(BeerDto beerDto) throws BadRequestException {
		BeerValidationHelper.validateBeer(beerDto);
		// TODO: Check if exists beer type and manufacturer
	}

	// endregion

}
