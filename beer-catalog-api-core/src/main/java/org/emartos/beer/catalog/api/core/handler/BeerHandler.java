package org.emartos.beer.catalog.api.core.handler;

import org.emartos.beer.catalog.api.core.exception.BadRequestException;
import org.emartos.beer.catalog.api.core.exception.NotFoundException;
import org.emartos.beer.catalog.api.repository.model.BeerDto;

import java.util.List;

public interface BeerHandler {

	BeerDto createBeer(BeerDto beerDto) throws BadRequestException;

	List<BeerDto> getAllBeers();

	BeerDto getBeerById(Long id) throws NotFoundException;

	BeerDto updateBeer(BeerDto beerDto) throws NotFoundException, BadRequestException;

	boolean deleteBeerById(Long id) throws NotFoundException;

}
