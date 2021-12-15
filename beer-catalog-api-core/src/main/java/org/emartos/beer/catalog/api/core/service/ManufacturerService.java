package org.emartos.beer.catalog.api.core.service;

import org.emartos.beer.catalog.api.core.exception.NotFoundException;
import org.emartos.beer.catalog.api.repository.model.ManufacturerDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ManufacturerService {

	ManufacturerDto createManufacturer(ManufacturerDto manufacturerDto);

	Page<ManufacturerDto> getAllManufacturers(Pageable pageable);

	ManufacturerDto getManufacturerById(Long id) throws NotFoundException;

	ManufacturerDto updateManufacturer(ManufacturerDto manufacturerDto) throws NotFoundException;

	boolean deleteManufacturerById(Long id) throws NotFoundException;

}
