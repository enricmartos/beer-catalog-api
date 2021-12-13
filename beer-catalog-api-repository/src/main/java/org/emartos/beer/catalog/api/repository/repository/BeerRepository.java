/**
 * Copyright (c) 2021
 *
 * Author: enricmartos
 */
package org.emartos.beer.catalog.api.repository.repository;

import org.emartos.beer.catalog.api.repository.model.BeerDto;

import java.util.List;
import java.util.Optional;

public interface BeerRepository {

	BeerDto create(BeerDto manufacturerDto);

	List<BeerDto> findAll();

	Optional<BeerDto> findById(Long id);

	BeerDto update(BeerDto manufacturerDto);

	boolean deleteById(Long id);
}
