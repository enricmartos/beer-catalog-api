package org.emartos.beer.catalog.api.core.service;

import org.emartos.beer.catalog.api.core.exception.NotFoundException;
import org.emartos.beer.catalog.api.repository.model.ManufacturerDto;

import java.util.List;

public interface ManufacturerService {

	ManufacturerDto createManufacturer(ManufacturerDto manufacturerDto);

	List<ManufacturerDto> getAllManufacturers();

	ManufacturerDto getManufacturerById(Long id) throws NotFoundException;

	ManufacturerDto updateManufacturer(ManufacturerDto manufacturerDto) throws NotFoundException;

	boolean deleteManufacturerById(Long id) throws NotFoundException;

}
