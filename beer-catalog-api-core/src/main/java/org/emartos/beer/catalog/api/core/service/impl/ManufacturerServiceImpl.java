package org.emartos.beer.catalog.api.core.service.impl;

import org.emartos.beer.catalog.api.core.exception.NotFoundException;
import org.emartos.beer.catalog.api.core.service.ManufacturerService;
import org.emartos.beer.catalog.api.repository.model.ManufacturerDto;
import org.emartos.beer.catalog.api.repository.repository.ManufacturerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ManufacturerServiceImpl implements ManufacturerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ManufacturerServiceImpl.class);

	private final ManufacturerRepository manufacturerRepository;

	public ManufacturerServiceImpl(@Qualifier("jpaManufacturerRepositoryImpl") ManufacturerRepository manufacturerRepository) {
		this.manufacturerRepository = manufacturerRepository;
	}

	@Override
	public ManufacturerDto createManufacturer(ManufacturerDto manufacturerDto) {
		LOGGER.debug(">> createManufacturer() manufacturerDto {}", manufacturerDto);

		ManufacturerDto manufacturerDtoPersisted = manufacturerRepository.create(manufacturerDto);

		LOGGER.debug("<< createManufacturer() ManufacturerDtoPersisted {}", manufacturerDtoPersisted);
		return manufacturerDtoPersisted;
	}

	@Override
	public Page<ManufacturerDto> getAllManufacturers(Pageable pageable) {
		LOGGER.debug(">> getAllManufacturers() pageable {}", pageable);

		Page<ManufacturerDto> manufacturerDtoList = manufacturerRepository.getAll(pageable);

		LOGGER.debug("<< getAllManufacturers() manufacturerDtoList {}", manufacturerDtoList);
		return manufacturerDtoList;
	}

	@Override
	public ManufacturerDto getManufacturerById(Long id) throws NotFoundException {
		LOGGER.debug(">> getManufacturerById() id {}", id);

		ManufacturerDto manufacturerDto = manufacturerRepository.getById(id).orElseThrow(NotFoundException::new);

		LOGGER.debug("<< getManufacturerById() manufacturerDto {}", manufacturerDto);
		return manufacturerDto;
	}

	@Override
	public ManufacturerDto updateManufacturer(ManufacturerDto manufacturerDto) throws NotFoundException {
		LOGGER.debug(">> updateManufacturer() manufacturerDto {}", manufacturerDto);

		if (manufacturerRepository.getById(manufacturerDto.getId()).isEmpty()) {
			throw new NotFoundException();
		}

		ManufacturerDto manufacturerDtoPersisted = manufacturerRepository.update(manufacturerDto);

		LOGGER.debug("<< updateManufacturer() ManufacturerDtoPersisted {}", manufacturerDtoPersisted);
		return manufacturerDtoPersisted;
	}

	@Override
	public boolean deleteManufacturerById(Long id) throws NotFoundException {
		LOGGER.debug(">> deleteManufacturerById() id {}", id);

		if (manufacturerRepository.getById(id).isEmpty()) {
			throw new NotFoundException();
		}

		boolean deleted = manufacturerRepository.deleteById(id);

		LOGGER.debug("<< deleteManufacturerById() deleted {}", deleted);
		return deleted;
	}

}
