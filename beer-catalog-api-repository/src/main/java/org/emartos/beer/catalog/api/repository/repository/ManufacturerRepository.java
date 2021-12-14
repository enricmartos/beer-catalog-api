/**
 * Copyright (c) 2021
 *
 * Author: enricmartos
 */
package org.emartos.beer.catalog.api.repository.repository;

import org.emartos.beer.catalog.api.repository.model.ManufacturerDto;

import java.util.List;
import java.util.Optional;

public interface ManufacturerRepository {

	ManufacturerDto create(ManufacturerDto manufacturerDto);

	List<ManufacturerDto> getAll();

	Optional<ManufacturerDto> getById(Long id);

	ManufacturerDto update(ManufacturerDto manufacturerDto);

	boolean deleteById(Long id);
}
