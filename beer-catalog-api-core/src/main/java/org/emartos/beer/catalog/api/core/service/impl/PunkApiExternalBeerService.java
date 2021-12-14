package org.emartos.beer.catalog.api.core.service.impl;

import com.google.gson.reflect.TypeToken;
import org.emartos.beer.catalog.api.core.PunkApiBeerResponseMapper;
import org.emartos.beer.catalog.api.core.client.HttpClient;
import org.emartos.beer.catalog.api.core.dto.punkapi.PunkApiBeerResponseDto;
import org.emartos.beer.catalog.api.core.exception.BeerCatalogApiException;
import org.emartos.beer.catalog.api.core.service.ExternalBeerService;
import org.emartos.beer.catalog.api.repository.model.BeerDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.emartos.beer.catalog.api.core.helper.HttpHelper.getUrl;
import static org.emartos.beer.catalog.api.core.helper.HttpHelper.parseResponseEntityToObject;

@Service
public class PunkApiExternalBeerService implements ExternalBeerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PunkApiExternalBeerService.class);

	@Value("${punk.api.url:https://api.punkapi.com}")
	private String punkApiHost;

	@Value("${punk.api.version:/v2}")
	private String punkApiVersion;

	@Value("${punk.api.get.beers.endpoint:/beers}")
	private String getBeersEndpoint;

	private final HttpClient httpClient;
	private final PunkApiBeerResponseMapper punkApiBeerResponseMapper;

	public PunkApiExternalBeerService(HttpClient httpClient, PunkApiBeerResponseMapper punkApiBeerResponseMapper) {
		this.httpClient = httpClient;
		this.punkApiBeerResponseMapper = punkApiBeerResponseMapper;
	}

	public Page<BeerDto> getAllBeersByName(String name, Pageable pageable) throws BeerCatalogApiException {
		LOGGER.debug(">> getAllBeersByName() name {} pageable {}", name, pageable);

		List<PunkApiBeerResponseDto> punkApiBeerResponseDtoList = new ArrayList<>();
		String url = getUrl(punkApiHost, punkApiVersion, getBeersEndpoint,
				"?page=", String.valueOf(pageable.getPageNumber() + 1),
				"&per_page=", String.valueOf(pageable.getPageSize()),
				"&beer_name=", name);

		ResponseEntity<String> responseEntity = httpClient.get(url);
		if (responseEntity != null) {
			punkApiBeerResponseDtoList = (List<PunkApiBeerResponseDto>) parseResponseEntityToObject(responseEntity, new TypeToken<List<PunkApiBeerResponseDto>>() {}.getType(), false);
		}
		List<BeerDto> beerDtoList = punkApiBeerResponseMapper.punkApiBeerResponseListDtoToBeerListDto(punkApiBeerResponseDtoList);
		Page<BeerDto> beerDtoPage = new PageImpl<>(beerDtoList, pageable, beerDtoList.size());

		LOGGER.debug("<< getAllBeersByName() beerDtoPage {}", punkApiBeerResponseDtoList);
		return beerDtoPage;
	}

}

