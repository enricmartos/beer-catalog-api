package org.emartos.beer.catalog.api.core.service;

import org.emartos.beer.catalog.api.core.exception.BeerCatalogApiException;
import org.emartos.beer.catalog.api.repository.model.BeerDto;
import org.emartos.beer.catalog.api.repository.model.BeerFilterDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ExternalBeerService {

	Page<BeerDto> getAllBeersByParams(BeerFilterDto beerFilterDto, Pageable pageable) throws BeerCatalogApiException;

}
