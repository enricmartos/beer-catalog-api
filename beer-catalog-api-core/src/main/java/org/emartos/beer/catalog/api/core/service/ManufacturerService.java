package org.emartos.beer.catalog.api.core.service;

import org.emartos.beer.catalog.api.repository.model.ManufacturerDto;

import java.util.List;

public interface ManufacturerService {

	ManufacturerDto createManufacturer(ManufacturerDto manufacturerDto);

	List<ManufacturerDto> getAllManufacturers();

	ManufacturerDto getManufacturerById(Long id);

	ManufacturerDto updateManufacturer(ManufacturerDto manufacturerDto);

	boolean deleteManufacturerById(Long id);

}
