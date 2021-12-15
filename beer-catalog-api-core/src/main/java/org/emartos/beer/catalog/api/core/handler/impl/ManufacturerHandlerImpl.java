package org.emartos.beer.catalog.api.core.handler.impl;

import org.emartos.beer.catalog.api.core.exception.BadRequestException;
import org.emartos.beer.catalog.api.core.exception.NotFoundException;
import org.emartos.beer.catalog.api.core.handler.ManufacturerHandler;
import org.emartos.beer.catalog.api.core.service.ManufacturerService;
import org.emartos.beer.catalog.api.repository.model.ManufacturerDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.emartos.beer.catalog.api.core.helper.ValidationHelper.checkNotNull;

@Service
public class ManufacturerHandlerImpl implements ManufacturerHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(ManufacturerHandlerImpl.class);

	private final ManufacturerService manufacturerService;

	public ManufacturerHandlerImpl(ManufacturerService manufacturerService) {
		this.manufacturerService = manufacturerService;
	}

	@Override
	public ManufacturerDto createManufacturer(ManufacturerDto manufacturerDto) throws BadRequestException {
		LOGGER.debug(">> createManufacturer() manufacturerDto {}", manufacturerDto);

		validateManufacturer(manufacturerDto);
		ManufacturerDto manufacturerDtoPersisted = manufacturerService.createManufacturer(manufacturerDto);

		LOGGER.debug("<< createManufacturer() ManufacturerDtoPersisted {}", manufacturerDtoPersisted);
		return manufacturerDtoPersisted;
	}

	@Override
	public List<ManufacturerDto> getAllManufacturers() {
		LOGGER.debug(">> getAllManufacturers()");

		List<ManufacturerDto> manufacturerDtoList = manufacturerService.getAllManufacturers();

		LOGGER.debug("<< getAllManufacturers() manufacturerDtoList {}", manufacturerDtoList);
		return manufacturerDtoList;
	}

	@Override
	public ManufacturerDto getManufacturerById(Long id) throws NotFoundException {
		LOGGER.debug(">> getManufacturerById() id {}", id);

		ManufacturerDto manufacturerDto = manufacturerService.getManufacturerById(id);

		LOGGER.debug("<< getManufacturerById() manufacturerDto {}", manufacturerDto);
		return manufacturerDto;
	}

	@Override
	public ManufacturerDto updateManufacturer(ManufacturerDto manufacturerDto) throws NotFoundException, BadRequestException {
		LOGGER.debug(">> updateManufacturer() manufacturerDto {}", manufacturerDto);

		validateManufacturer(manufacturerDto);
		ManufacturerDto manufacturerDtoPersisted = manufacturerService.updateManufacturer(manufacturerDto);

		LOGGER.debug("<< updateManufacturer() ManufacturerDtoPersisted {}", manufacturerDtoPersisted);
		return manufacturerDtoPersisted;
	}

	@Override
	public boolean deleteManufacturerById(Long id) throws NotFoundException {
		LOGGER.debug(">> deleteManufacturerById() id {}", id);

		boolean deleted = manufacturerService.deleteManufacturerById(id);

		LOGGER.debug("<< deleteManufacturerById() deleted {}", deleted);
		return deleted;
	}

	// region Private methods

	private void validateManufacturer(ManufacturerDto manufacturerDto) throws BadRequestException {
		checkNullParams(manufacturerDto);
	}

	private void checkNullParams(ManufacturerDto manufacturerDto) throws BadRequestException {
		checkNotNull(manufacturerDto.getName(), "name");
		checkNotNull(manufacturerDto.getNationality(), "nationality");
	}

	// endregion

}
