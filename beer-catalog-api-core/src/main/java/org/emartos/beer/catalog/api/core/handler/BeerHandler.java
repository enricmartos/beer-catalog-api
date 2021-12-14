package org.emartos.beer.catalog.api.core.handler;

import org.emartos.beer.catalog.api.core.exception.BadRequestException;
import org.emartos.beer.catalog.api.core.exception.BeerCatalogApiException;
import org.emartos.beer.catalog.api.core.exception.NotFoundException;
import org.emartos.beer.catalog.api.repository.model.BeerDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BeerHandler {

	BeerDto createBeer(BeerDto beerDto) throws BadRequestException;

	Page<BeerDto> getAllBeers(Pageable pageable);

	Page<BeerDto> getAllBeersByName(String name, Pageable pageable) throws BeerCatalogApiException;

	BeerDto getBeerById(Long id) throws NotFoundException;

	BeerDto updateBeer(BeerDto beerDto) throws NotFoundException, BadRequestException;

	boolean deleteBeerById(Long id) throws NotFoundException;

}
