package org.emartos.beer.catalog.api.core.handler;

import org.emartos.beer.catalog.api.core.exception.BadRequestException;
import org.emartos.beer.catalog.api.core.exception.NotFoundException;
import org.emartos.beer.catalog.api.repository.model.ManufacturerDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ManufacturerHandler {

	ManufacturerDto createManufacturer(ManufacturerDto manufacturerDto) throws BadRequestException;

	Page<ManufacturerDto> getAllManufacturers(Pageable pageable);

	ManufacturerDto getManufacturerById(Long id) throws NotFoundException;

	ManufacturerDto updateManufacturer(ManufacturerDto manufacturerDto) throws NotFoundException, BadRequestException;

	boolean deleteManufacturerById(Long id) throws NotFoundException;

}
