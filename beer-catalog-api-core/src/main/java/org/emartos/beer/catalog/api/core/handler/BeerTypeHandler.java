package org.emartos.beer.catalog.api.core.handler;

import org.emartos.beer.catalog.api.core.exception.BadRequestException;
import org.emartos.beer.catalog.api.core.exception.NotFoundException;
import org.emartos.beer.catalog.api.repository.model.BeerTypeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BeerTypeHandler {

	BeerTypeDto createBeerType(BeerTypeDto beerTypeDto) throws BadRequestException;

	Page<BeerTypeDto> getAllBeerTypes(Pageable pageable);

	BeerTypeDto getBeerTypeById(Long id) throws NotFoundException;

	BeerTypeDto updateBeerType(BeerTypeDto beerTypeDto) throws NotFoundException, BadRequestException;

	boolean deleteBeerTypeById(Long id) throws NotFoundException;

}
