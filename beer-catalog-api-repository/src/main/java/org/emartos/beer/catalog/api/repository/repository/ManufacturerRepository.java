/**
 * Copyright (c) 2021
 *
 * Author: enricmartos
 */
package org.emartos.beer.catalog.api.repository.repository;

import org.emartos.beer.catalog.api.repository.model.ManufacturerDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ManufacturerRepository {

	ManufacturerDto create(ManufacturerDto manufacturerDto);

	Page<ManufacturerDto> getAll(Pageable pageable);

	Optional<ManufacturerDto> getById(Long id);

	ManufacturerDto update(ManufacturerDto manufacturerDto);

	boolean deleteById(Long id);
}
