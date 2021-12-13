/**
 * Copyright (c) 2021
 *
 * Author: enricmartos
 */
package org.emartos.beer.catalog.api.repository.repository;

import org.emartos.beer.catalog.api.repository.model.ManufacturerDto;

import java.util.List;

public interface ManufacturerRepository {

	ManufacturerDto create(ManufacturerDto manufacturerDto);

	List<ManufacturerDto> findAll();

	ManufacturerDto findById(Long id);

	ManufacturerDto update(ManufacturerDto manufacturerDto);

	boolean deleteById(Long id);
}
