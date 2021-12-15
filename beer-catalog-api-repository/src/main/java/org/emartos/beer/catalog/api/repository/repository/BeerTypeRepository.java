/**
 * Copyright (c) 2021
 *
 * Author: enricmartos
 */
package org.emartos.beer.catalog.api.repository.repository;

import org.emartos.beer.catalog.api.repository.model.BeerTypeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BeerTypeRepository {

	BeerTypeDto create(BeerTypeDto beerTypeDto);

	Page<BeerTypeDto> getAll(Pageable pageable);

	Optional<BeerTypeDto> getById(Long id);

	BeerTypeDto update(BeerTypeDto beerTypeDto);

	boolean deleteById(Long id);
}
