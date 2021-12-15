package org.emartos.beer.catalog.api.core.service;

import org.emartos.beer.catalog.api.core.exception.NotFoundException;
import org.emartos.beer.catalog.api.repository.model.BeerTypeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BeerTypeService {

	BeerTypeDto createBeerType(BeerTypeDto beerTypeDto);

	Page<BeerTypeDto> getAllBeerTypes(Pageable pageable);

	BeerTypeDto getBeerTypeById(Long id) throws NotFoundException;

	BeerTypeDto updateBeerType(BeerTypeDto beerTypeDto) throws NotFoundException;

	boolean deleteBeerTypeById(Long id) throws NotFoundException;

}
