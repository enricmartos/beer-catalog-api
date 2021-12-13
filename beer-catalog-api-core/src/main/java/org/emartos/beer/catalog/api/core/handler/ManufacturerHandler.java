package org.emartos.beer.catalog.api.core.handler;

import org.emartos.beer.catalog.api.core.exception.BadRequestException;
import org.emartos.beer.catalog.api.core.exception.NotFoundException;
import org.emartos.beer.catalog.api.repository.model.ManufacturerDto;

import java.util.List;

public interface ManufacturerHandler {

	ManufacturerDto createManufacturer(ManufacturerDto manufacturerDto) throws BadRequestException;

	List<ManufacturerDto> getAllManufacturers();

	ManufacturerDto getManufacturerById(Long id) throws NotFoundException;

	ManufacturerDto updateManufacturer(ManufacturerDto manufacturerDto) throws NotFoundException, BadRequestException;

	boolean deleteManufacturerById(Long id) throws NotFoundException;

}
