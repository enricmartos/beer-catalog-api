package org.emartos.beer.catalog.api.repository.repository.inmemory;

import org.emartos.beer.catalog.api.repository.model.BeerDto;
import org.emartos.beer.catalog.api.repository.repository.BeerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Component
@Qualifier("inMemoryBeerRepositoryImpl")
public class InMemoryBeerRepository implements BeerRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(InMemoryBeerRepository.class);

	private final Map<Long, BeerDto> beerMap = new HashMap<>();
	private final AtomicLong currentId = new AtomicLong();

	@Override
	public BeerDto create(BeerDto beerDto) {
		LOGGER.debug(">> create() beerDto {}", beerDto);

		Long id = currentId.incrementAndGet();
		beerDto.setId(id);
		beerMap.putIfAbsent(id, beerDto);

		LOGGER.debug("<< create() beerDto {}", beerDto);
		return beerDto;
	}

	@Override
	public List<BeerDto> findAll() {
		LOGGER.debug(">> findAll()");

		List<BeerDto> beerDtoList = new ArrayList<> (beerMap.values());

		LOGGER.debug("<< findById() beerDtoList {}", beerDtoList);
		return beerDtoList;
	}

	@Override
	public Optional<BeerDto> findById(Long id) {
		LOGGER.debug(">> findById() id {}", id);

		Optional<BeerDto> beerDtoOptional = Optional.ofNullable(beerMap.get(id));

		LOGGER.debug("<< findById() beerDtoOptional {}", beerDtoOptional);
		return beerDtoOptional;
	}

	@Override
	public BeerDto update(BeerDto beerDto) {
		LOGGER.debug(">> update() beerDto {}", beerDto);

		beerMap.put(beerDto.getId(), beerDto);

		LOGGER.debug("<< update() beerDto {}", beerDto);
		return beerDto;
	}

	@Override
	public boolean deleteById(Long id) {
		LOGGER.debug(">> deleteById() id {}", id);

		boolean deleted = false;
		if (beerMap.get(id) != null) {
			BeerDto beerDto = beerMap.get(id);
			deleted = beerMap.remove(id, beerDto);
		}

		LOGGER.debug("<< deleteById() deleted {}", deleted);
		return deleted;
	}
}
