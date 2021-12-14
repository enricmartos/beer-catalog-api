package org.emartos.beer.catalog.api.core.service;

import org.emartos.beer.catalog.api.core.exception.BeerCatalogApiException;
import org.emartos.beer.catalog.api.repository.model.BeerDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ExternalBeerService {

	Page<BeerDto> getAllBeersByName(String name, Pageable pageable) throws BeerCatalogApiException;

}
