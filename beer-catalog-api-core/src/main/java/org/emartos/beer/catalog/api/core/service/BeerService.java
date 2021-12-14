package org.emartos.beer.catalog.api.core.service;

import org.emartos.beer.catalog.api.core.exception.NotFoundException;
import org.emartos.beer.catalog.api.repository.model.BeerDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BeerService {

	BeerDto createBeer(BeerDto beerDto);

	Page<BeerDto> getAllBeers(Pageable pageable);

	Page<BeerDto> getAllBeersByName(String name, Pageable pageable);

	BeerDto getBeerById(Long id) throws NotFoundException;

	BeerDto updateBeer(BeerDto beerDto) throws NotFoundException;

	boolean deleteBeerById(Long id) throws NotFoundException;

}
