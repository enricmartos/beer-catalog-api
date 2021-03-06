package org.emartos.beer.catalog.api.repository.repository.inmemory;

import org.emartos.beer.catalog.api.repository.model.ManufacturerDto;
import org.emartos.beer.catalog.api.repository.repository.ManufacturerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Component
@Qualifier("inMemoryManufacturerRepositoryImpl")
public class InMemoryManufacturerRepository implements ManufacturerRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(InMemoryManufacturerRepository.class);

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
	public Page<ManufacturerDto> getAll(Pageable pageable) {
		LOGGER.debug(">> findAll()");

		Page<ManufacturerDto> manufacturerDtoList = new PageImpl<>(new ArrayList<> (manufacturerMap.values()));

		LOGGER.debug("<< findById() manufacturerDtoList {}", manufacturerDtoList);
		return manufacturerDtoList;
	}

	@Override
	public Optional<ManufacturerDto> getById(Long id) {
		LOGGER.debug(">> findById() id {}", id);

		Optional<ManufacturerDto> manufacturerDtoOptional = Optional.ofNullable(manufacturerMap.get(id));

		LOGGER.debug("<< findById() manufacturerDtoOptional {}", manufacturerDtoOptional);
		return manufacturerDtoOptional;
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
