package org.emartos.beer.catalog.api.core.service.impl;

import org.emartos.beer.catalog.api.core.service.ManufacturerService;
import org.emartos.beer.catalog.api.repository.model.ManufacturerDto;
import org.emartos.beer.catalog.api.repository.repository.ManufacturerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManufacturerServiceImpl implements ManufacturerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ManufacturerServiceImpl.class);

	private final ManufacturerRepository manufacturerRepository;

	public ManufacturerServiceImpl(@Qualifier("inMemoryMemoryRepositoryImpl") ManufacturerRepository manufacturerRepository) {
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
	public List<ManufacturerDto> getAllManufacturers() {
		LOGGER.debug(">> getAllManufacturers()");

		List<ManufacturerDto> manufacturerDtoList = manufacturerRepository.findAll();

		LOGGER.debug("<< getAllManufacturers() manufacturerDtoList {}", manufacturerDtoList);
		return manufacturerDtoList;
	}

	@Override
	public ManufacturerDto getManufacturerById(Long id) {
		LOGGER.debug(">> getManufacturerById() id {}", id);

		ManufacturerDto manufacturerDto = manufacturerRepository.findById(id);

		LOGGER.debug("<< getManufacturerById() manufacturerDto {}", manufacturerDto);
		return manufacturerDto;
	}

	@Override
	public ManufacturerDto updateManufacturer(ManufacturerDto manufacturerDto) {
		LOGGER.debug(">> updateManufacturer() manufacturerDto {}", manufacturerDto);

		ManufacturerDto manufacturerDtoPersisted = manufacturerRepository.update(manufacturerDto);

		LOGGER.debug("<< updateManufacturer() ManufacturerDtoPersisted {}", manufacturerDtoPersisted);
		return manufacturerDtoPersisted;
	}

	@Override
	public boolean deleteManufacturerById(Long id) {
		LOGGER.debug(">> deleteManufacturerById() id {}", id);

		boolean deleted = manufacturerRepository.deleteById(id);

		LOGGER.debug("<< deleteManufacturerById() deleted {}", deleted);
		return deleted;
	}

}
