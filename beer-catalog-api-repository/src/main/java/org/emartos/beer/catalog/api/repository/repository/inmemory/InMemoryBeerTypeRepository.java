package org.emartos.beer.catalog.api.repository.repository.inmemory;

import org.emartos.beer.catalog.api.repository.model.BeerTypeDto;
import org.emartos.beer.catalog.api.repository.repository.BeerTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Component
@Qualifier("inMemoryBeerTypeRepositoryImpl")
public class InMemoryBeerTypeRepository implements BeerTypeRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(InMemoryBeerTypeRepository.class);

	private final Map<Long, BeerTypeDto> beerTypeMap = new HashMap<>();
	private final AtomicLong currentId = new AtomicLong();

	@Override
	public BeerTypeDto create(BeerTypeDto beerTypeDto) {
		LOGGER.debug(">> create() beerTypeDto {}", beerTypeDto);

		Long id = currentId.incrementAndGet();
		beerTypeDto.setId(id);
		beerTypeMap.putIfAbsent(id, beerTypeDto);

		LOGGER.debug("<< create() beerTypeDto {}", beerTypeDto);
		return beerTypeDto;
	}

	@Override
	public List<BeerTypeDto> getAll() {
		LOGGER.debug(">> findAll()");

		List<BeerTypeDto> beerTypeDtoList = new ArrayList<> (beerTypeMap.values());

		LOGGER.debug("<< findById() beerTypeDtoList {}", beerTypeDtoList);
		return beerTypeDtoList;
	}

	@Override
	public Optional<BeerTypeDto> getById(Long id) {
		LOGGER.debug(">> findById() id {}", id);

		Optional<BeerTypeDto> beerTypeDtoOptional = Optional.ofNullable(beerTypeMap.get(id));

		LOGGER.debug("<< findById() beerTypeDtoOptional {}", beerTypeDtoOptional);
		return beerTypeDtoOptional;
	}

	@Override
	public BeerTypeDto update(BeerTypeDto beerTypeDto) {
		LOGGER.debug(">> update() beerTypeDto {}", beerTypeDto);

		beerTypeMap.put(beerTypeDto.getId(), beerTypeDto);

		LOGGER.debug("<< update() beerTypeDto {}", beerTypeDto);
		return beerTypeDto;
	}

	@Override
	public boolean deleteById(Long id) {
		LOGGER.debug(">> deleteById() id {}", id);

		boolean deleted = false;
		if (beerTypeMap.get(id) != null) {
			BeerTypeDto beerTypeDto = beerTypeMap.get(id);
			deleted = beerTypeMap.remove(id, beerTypeDto);
		}

		LOGGER.debug("<< deleteById() deleted {}", deleted);
		return deleted;
	}
}
