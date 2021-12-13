package org.emartos.beer.catalog.api.core.handler;

import org.emartos.beer.catalog.api.core.exception.BadRequestException;
import org.emartos.beer.catalog.api.core.exception.NotFoundException;
import org.emartos.beer.catalog.api.repository.model.BeerTypeDto;

import java.util.List;

public interface BeerTypeHandler {

	BeerTypeDto createBeerType(BeerTypeDto beerTypeDto) throws BadRequestException;

	List<BeerTypeDto> getAllBeerTypes();

	BeerTypeDto getBeerTypeById(Long id) throws NotFoundException;

	BeerTypeDto updateBeerType(BeerTypeDto beerTypeDto) throws NotFoundException, BadRequestException;

	boolean deleteBeerTypeById(Long id) throws NotFoundException;

}
