package org.emartos.beer.catalog.api.repository.repository.inmemory;

import org.emartos.beer.catalog.api.repository.model.ManufacturerDto;
import org.emartos.beer.catalog.api.repository.repository.ManufacturerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Component
@Qualifier("inMemoryMemoryRepositoryImpl")
public class InMemoryManufacturer implements ManufacturerRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(InMemoryManufacturer.class);

	private final Map<Long, ManufacturerDto> manufacturerMap = new HashMap<>();
	private final AtomicLong currentId = new AtomicLong();

	@Override
	public ManufacturerDto create(ManufacturerDto manufacturerDto) {
		LOGGER.debug(">> create() manufacturerDto {}", manufacturerDto);

		Long id = currentId.incrementAndGet();
		manufacturerDto.setId(id);
		manufacturerMap.putIfAbsent(id, manufacturerDto);

		LOGGER.debug("<< create() manufacturerDto {}", manufacturerDto);
		return manufacturerDto;
	}

	@Override
	public List<ManufacturerDto> findAll() {
		LOGGER.debug(">> findAll()");

		List<ManufacturerDto> manufacturerDtoList = new ArrayList<> (manufacturerMap.values());

		LOGGER.debug("<< findById() manufacturerDtoList {}", manufacturerDtoList);
		return manufacturerDtoList;
	}

	@Override
	public ManufacturerDto findById(Long id) {
		LOGGER.debug(">> findById() id {}", id);

		ManufacturerDto manufacturerDto = manufacturerMap.computeIfAbsent(id, s -> new ManufacturerDto());

		LOGGER.debug("<< findById() manufacturerDto {}", manufacturerDto);
		return manufacturerDto;
	}

	@Override
	public ManufacturerDto update(ManufacturerDto manufacturerDto) {
		LOGGER.debug(">> update() manufacturerDto {}", manufacturerDto);

		manufacturerMap.put(manufacturerDto.getId(), manufacturerDto);

		LOGGER.debug("<< update() manufacturerDto {}", manufacturerDto);
		return manufacturerDto;
	}

	@Override
	public boolean deleteById(Long id) {
		LOGGER.debug(">> deleteById() id {}", id);

		boolean deleted = false;
		if (manufacturerMap.get(id) != null) {
			ManufacturerDto manufacturerDto = manufacturerMap.get(id);
			deleted = manufacturerMap.remove(id, manufacturerDto);
		}

		LOGGER.debug("<< deleteById() deleted {}", deleted);
		return deleted;
	}
}
