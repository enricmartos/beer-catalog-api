package org.emartos.beer.catalog.api.core.service;

import org.emartos.beer.catalog.api.core.exception.NotFoundException;
import org.emartos.beer.catalog.api.repository.model.BeerTypeDto;

import java.util.List;

public interface BeerTypeService {

	BeerTypeDto createBeerType(BeerTypeDto beerTypeDto);

	List<BeerTypeDto> getAllBeerTypes();

	BeerTypeDto getBeerTypeById(Long id) throws NotFoundException;

	BeerTypeDto updateBeerType(BeerTypeDto beerTypeDto) throws NotFoundException;

	boolean deleteBeerTypeById(Long id) throws NotFoundException;

}
