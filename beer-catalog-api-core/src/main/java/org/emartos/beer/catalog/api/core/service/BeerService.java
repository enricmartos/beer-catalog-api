package org.emartos.beer.catalog.api.core.service;

import org.emartos.beer.catalog.api.core.exception.NotFoundException;
import org.emartos.beer.catalog.api.repository.model.BeerDto;

import java.util.List;

public interface BeerService {

	BeerDto createBeer(BeerDto beerDto);

	List<BeerDto> getAllBeers();

	BeerDto getBeerById(Long id) throws NotFoundException;

	BeerDto updateBeer(BeerDto beerDto) throws NotFoundException;

	boolean deleteBeerById(Long id) throws NotFoundException;

}
