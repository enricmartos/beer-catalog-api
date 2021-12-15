package org.emartos.beer.catalog.api.core.service;

import org.emartos.beer.catalog.api.core.exception.NotFoundException;
import org.emartos.beer.catalog.api.repository.model.BeerDto;
import org.emartos.beer.catalog.api.repository.model.BeerFilterDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BeerService {

	BeerDto createBeer(BeerDto beerDto);

	Page<BeerDto> getAllBeersByParams(BeerFilterDto beerFilterDto, Pageable pageable);

	BeerDto getBeerById(Long id) throws NotFoundException;

	BeerDto updateBeer(BeerDto beerDto) throws NotFoundException;

	boolean deleteBeerById(Long id) throws NotFoundException;

}
