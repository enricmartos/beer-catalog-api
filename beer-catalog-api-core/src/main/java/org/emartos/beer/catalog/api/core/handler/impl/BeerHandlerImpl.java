package org.emartos.beer.catalog.api.core.handler.impl;

import org.emartos.beer.catalog.api.core.exception.BadRequestException;
import org.emartos.beer.catalog.api.core.exception.BeerCatalogApiException;
import org.emartos.beer.catalog.api.core.exception.NotFoundException;
import org.emartos.beer.catalog.api.core.handler.BeerHandler;
import org.emartos.beer.catalog.api.core.service.BeerService;
import org.emartos.beer.catalog.api.core.service.BeerTypeService;
import org.emartos.beer.catalog.api.core.service.ExternalBeerService;
import org.emartos.beer.catalog.api.core.service.ManufacturerService;
import org.emartos.beer.catalog.api.repository.model.BeerDto;
import org.emartos.beer.catalog.api.repository.model.BeerFilterDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static org.emartos.beer.catalog.api.core.helper.ValidationHelper.checkNotNull;

@Service
public class BeerHandlerImpl implements BeerHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(BeerHandlerImpl.class);

	private final BeerService beerService;
	private final ExternalBeerService externalBeerService;
	private final ManufacturerService manufacturerService;
	private final BeerTypeService beerTypeService;

	public BeerHandlerImpl(BeerService beerService, ExternalBeerService externalBeerService, ManufacturerService manufacturerService, BeerTypeService beerTypeService) {
		this.beerService = beerService;
		this.externalBeerService = externalBeerService;
		this.manufacturerService = manufacturerService;
		this.beerTypeService = beerTypeService;
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
	public Page<BeerDto> getAllBeersByParams(BeerFilterDto beerFilterDto, Pageable pageable) throws BeerCatalogApiException {
		LOGGER.debug(">> getAllBeersByParams() beerFilterDto {} pageable {}", beerFilterDto, pageable);

		Page<BeerDto> beerDtoPage = beerService.getAllBeersByParams(beerFilterDto, pageable);
		if (beerDtoPage.getContent().isEmpty()) {
			beerDtoPage = externalBeerService.getAllBeersByParams(beerFilterDto, pageable);
		}

		LOGGER.debug("<< getAllBeersByParams() beerDtoPage {}", beerDtoPage);
		return beerDtoPage;
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
		checkNullParams(beerDto);

		try {
			beerTypeService.getBeerTypeById(beerDto.getBeerTypeId());
		} catch (NotFoundException e) {
			throw new BadRequestException(String.format("Beer Type with id %s does not exist", beerDto.getBeerTypeId()));
		}

		try {
			manufacturerService.getManufacturerById(beerDto.getManufacturerId());
		} catch (NotFoundException e) {
			throw new BadRequestException(String.format("Manufacturer with id %s does not exist", beerDto.getManufacturerId()));
		}
	}

	private void checkNullParams(BeerDto beerDto) throws BadRequestException {
		checkNotNull(beerDto.getName(), "name");
		checkNotNull(beerDto.getDescription(), "description");
		checkNotNull(beerDto.getGraduation(), "graduation");
	}

	// endregion

}
