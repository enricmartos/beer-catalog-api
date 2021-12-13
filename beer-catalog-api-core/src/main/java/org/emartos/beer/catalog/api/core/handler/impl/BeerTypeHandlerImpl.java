package org.emartos.beer.catalog.api.core.handler.impl;

import org.emartos.beer.catalog.api.core.exception.BadRequestException;
import org.emartos.beer.catalog.api.core.exception.NotFoundException;
import org.emartos.beer.catalog.api.core.handler.BeerTypeHandler;
import org.emartos.beer.catalog.api.core.service.BeerTypeService;
import org.emartos.beer.catalog.api.repository.model.BeerTypeDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.emartos.beer.catalog.api.core.helper.BeerTypeValidationHelper.validateBeerType;

@Service
public class BeerTypeHandlerImpl implements BeerTypeHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(BeerTypeHandlerImpl.class);

	private final BeerTypeService beerTypeService;

	public BeerTypeHandlerImpl(BeerTypeService beerTypeService) {
		this.beerTypeService = beerTypeService;
	}

	@Override
	public BeerTypeDto createBeerType(BeerTypeDto beerTypeDto) throws BadRequestException {
		LOGGER.debug(">> createBeerType() beerTypeDto {}", beerTypeDto);

		validateBeerType(beerTypeDto);
		BeerTypeDto beerTypeDtoPersisted = beerTypeService.createBeerType(beerTypeDto);

		LOGGER.debug("<< createBeerType() beerTypeDtoPersisted {}", beerTypeDtoPersisted);
		return beerTypeDtoPersisted;
	}

	@Override
	public List<BeerTypeDto> getAllBeerTypes() {
		LOGGER.debug(">> getAllBeerTypes()");

		List<BeerTypeDto> beerTypeDtoList = beerTypeService.getAllBeerTypes();

		LOGGER.debug("<< getAllBeerTypes() beerTypeDtoList {}", beerTypeDtoList);
		return beerTypeDtoList;
	}

	@Override
	public BeerTypeDto getBeerTypeById(Long id) throws NotFoundException {
		LOGGER.debug(">> getBeerTypeById() id {}", id);

		BeerTypeDto beerTypeDto = beerTypeService.getBeerTypeById(id);

		LOGGER.debug("<< getBeerTypeById() beerTypeDto {}", beerTypeDto);
		return beerTypeDto;
	}

	@Override
	public BeerTypeDto updateBeerType(BeerTypeDto beerTypeDto) throws NotFoundException, BadRequestException {
		LOGGER.debug(">> updateBeerType() beerTypeDto {}", beerTypeDto);

		validateBeerType(beerTypeDto);
		BeerTypeDto beerTypeDtoPersisted = beerTypeService.updateBeerType(beerTypeDto);

		LOGGER.debug("<< updateBeerType() beerTypeDtoPersisted {}", beerTypeDtoPersisted);
		return beerTypeDtoPersisted;
	}

	@Override
	public boolean deleteBeerTypeById(Long id) throws NotFoundException {
		LOGGER.debug(">> deleteBeerTypeById() id {}", id);

		boolean deleted = beerTypeService.deleteBeerTypeById(id);

		LOGGER.debug("<< deleteBeerTypeById() deleted {}", deleted);
		return deleted;
	}

}
