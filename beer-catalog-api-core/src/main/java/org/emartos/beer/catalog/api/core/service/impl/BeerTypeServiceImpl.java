package org.emartos.beer.catalog.api.core.service.impl;

import org.emartos.beer.catalog.api.core.exception.NotFoundException;
import org.emartos.beer.catalog.api.core.service.BeerTypeService;
import org.emartos.beer.catalog.api.repository.model.BeerTypeDto;
import org.emartos.beer.catalog.api.repository.repository.BeerTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BeerTypeServiceImpl implements BeerTypeService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BeerTypeServiceImpl.class);

	private final BeerTypeRepository beerTypeRepository;

	public BeerTypeServiceImpl(@Qualifier("jpaBeerTypeRepositoryImpl") BeerTypeRepository beerTypeRepository) {
		this.beerTypeRepository = beerTypeRepository;
	}

	@Override
	public BeerTypeDto createBeerType(BeerTypeDto beerTypeDto) {
		LOGGER.debug(">> createBeerType() beerTypeDto {}", beerTypeDto);

		BeerTypeDto beerTypeDtoPersisted = beerTypeRepository.create(beerTypeDto);

		LOGGER.debug("<< createBeerType() BeerTypeDtoPersisted {}", beerTypeDtoPersisted);
		return beerTypeDtoPersisted;
	}

	@Override
	public Page<BeerTypeDto> getAllBeerTypes(Pageable pageable) {
		LOGGER.debug(">> getAllBeerTypes() pageable {}", pageable);

		Page<BeerTypeDto> beerTypeDtoPage = beerTypeRepository.getAll(pageable);

		LOGGER.debug("<< getAllBeerTypes() beerTypeDtoPage {}", beerTypeDtoPage);
		return beerTypeDtoPage;
	}

	@Override
	public BeerTypeDto getBeerTypeById(Long id) throws NotFoundException {
		LOGGER.debug(">> getBeerTypeById() id {}", id);

		BeerTypeDto beerTypeDto = beerTypeRepository.getById(id).orElseThrow(NotFoundException::new);

		LOGGER.debug("<< getBeerTypeById() beerTypeDto {}", beerTypeDto);
		return beerTypeDto;
	}

	@Override
	public BeerTypeDto updateBeerType(BeerTypeDto beerTypeDto) throws NotFoundException {
		LOGGER.debug(">> updateBeerType() beerTypeDto {}", beerTypeDto);

		if (beerTypeRepository.getById(beerTypeDto.getId()).isEmpty()) {
			throw new NotFoundException();
		}

		BeerTypeDto beerTypeDtoPersisted = beerTypeRepository.update(beerTypeDto);

		LOGGER.debug("<< updateBeerType() BeerTypeDtoPersisted {}", beerTypeDtoPersisted);
		return beerTypeDtoPersisted;
	}

	@Override
	public boolean deleteBeerTypeById(Long id) throws NotFoundException {
		LOGGER.debug(">> deleteBeerTypeById() id {}", id);

		if (beerTypeRepository.getById(id).isEmpty()) {
			throw new NotFoundException();
		}

		boolean deleted = beerTypeRepository.deleteById(id);

		LOGGER.debug("<< deleteBeerTypeById() deleted {}", deleted);
		return deleted;
	}

}
