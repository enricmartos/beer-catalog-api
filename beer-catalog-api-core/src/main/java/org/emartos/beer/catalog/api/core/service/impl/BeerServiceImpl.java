package org.emartos.beer.catalog.api.core.service.impl;

import org.emartos.beer.catalog.api.core.exception.NotFoundException;
import org.emartos.beer.catalog.api.core.service.BeerService;
import org.emartos.beer.catalog.api.repository.model.BeerDto;
import org.emartos.beer.catalog.api.repository.repository.BeerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BeerServiceImpl implements BeerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BeerServiceImpl.class);

	private final BeerRepository beerRepository;

	public BeerServiceImpl(@Qualifier("jpaBeerRepositoryImpl") BeerRepository beerRepository) {
		this.beerRepository = beerRepository;
	}

	@Override
	public BeerDto createBeer(BeerDto beerDto) {
		LOGGER.debug(">> createBeer() beerDto {}", beerDto);

		BeerDto beerDtoPersisted = beerRepository.create(beerDto);

		LOGGER.debug("<< createBeer() BeerDtoPersisted {}", beerDtoPersisted);
		return beerDtoPersisted;
	}

	@Override
	public Page<BeerDto> getAllBeers(Pageable pageable) {
		LOGGER.debug(">> getAllBeers() pageable {}", pageable);

		Page<BeerDto> beerDtoList = beerRepository.getAll(pageable);

		LOGGER.debug("<< getAllBeers() beerDtoList {}", beerDtoList);
		return beerDtoList;
	}

	@Override
	public BeerDto getBeerById(Long id) throws NotFoundException {
		LOGGER.debug(">> getBeerById() id {}", id);

		BeerDto beerDto = beerRepository.getById(id).orElseThrow(NotFoundException::new);

		LOGGER.debug("<< getBeerById() beerDto {}", beerDto);
		return beerDto;
	}

	@Override
	public BeerDto updateBeer(BeerDto beerDto) throws NotFoundException {
		LOGGER.debug(">> updateBeer() beerDto {}", beerDto);

		if (beerRepository.getById(beerDto.getId()).isEmpty()) {
			throw new NotFoundException();
		}

		BeerDto beerDtoPersisted = beerRepository.update(beerDto);

		LOGGER.debug("<< updateBeer() BeerDtoPersisted {}", beerDtoPersisted);
		return beerDtoPersisted;
	}

	@Override
	public boolean deleteBeerById(Long id) throws NotFoundException {
		LOGGER.debug(">> deleteBeerById() id {}", id);

		if (beerRepository.getById(id).isEmpty()) {
			throw new NotFoundException();
		}

		boolean deleted = beerRepository.deleteById(id);

		LOGGER.debug("<< deleteBeerById() deleted {}", deleted);
		return deleted;
	}

}
