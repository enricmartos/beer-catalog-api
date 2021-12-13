/**
 * Copyright (c) 2021
 *
 * Author: enricmartos
 */
package org.emartos.beer.catalog.api.repository.repository;

import org.emartos.beer.catalog.api.repository.model.BeerTypeDto;

import java.util.List;
import java.util.Optional;

public interface BeerTypeRepository {

	BeerTypeDto create(BeerTypeDto beerTypeDto);

	List<BeerTypeDto> findAll();

	Optional<BeerTypeDto> findById(Long id);

	BeerTypeDto update(BeerTypeDto beerTypeDto);

	boolean deleteById(Long id);
}
