/**
 * Copyright (c) 2021
 *
 * Author: enricmartos
 */
package org.emartos.beer.catalog.api.repository.repository;

import org.emartos.beer.catalog.api.repository.model.BeerDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BeerRepository {

	BeerDto create(BeerDto manufacturerDto);

	Page<BeerDto> getAll(Pageable pageable);

	Optional<BeerDto> getById(Long id);

	BeerDto update(BeerDto manufacturerDto);

	boolean deleteById(Long id);
}
