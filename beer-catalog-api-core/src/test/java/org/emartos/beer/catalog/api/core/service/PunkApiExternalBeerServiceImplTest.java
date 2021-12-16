package org.emartos.beer.catalog.api.core.service;

import com.google.gson.Gson;
import org.emartos.beer.catalog.api.core.client.HttpClient;
import org.emartos.beer.catalog.api.core.dto.punkapi.PunkApiBeerResponseDto;
import org.emartos.beer.catalog.api.core.exception.BeerCatalogApiException;
import org.emartos.beer.catalog.api.core.mapper.PunkApiBeerResponseMapper;
import org.emartos.beer.catalog.api.core.service.impl.PunkApiExternalBeerService;
import org.emartos.beer.catalog.api.repository.model.BeerDto;
import org.emartos.beer.catalog.api.repository.model.BeerFilterDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.List;

import static org.emartos.beer.catalog.api.core.BeerCatalogApiCoreDataFactory.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class PunkApiExternalBeerServiceImplTest {

	private static final String PUNK_API_BEERS_MOCK_URL = "https://api.punkapi.com/v2/beers?page=1&per_page=25&beer_name=Beer_name&abv_gt=4.0&abv_lt=5.0";

	@Mock
	private HttpClient httpClient;

	@Mock
	private PunkApiBeerResponseMapper punkApiBeerResponseMapper;

	private ExternalBeerService externalBeerService;

	@Before
	public void init() {
		externalBeerService = new PunkApiExternalBeerService(httpClient, punkApiBeerResponseMapper);
		ReflectionTestUtils.setField(externalBeerService, "punkApiHost", "https://api.punkapi.com");
		ReflectionTestUtils.setField(externalBeerService, "punkApiVersion", "/v2");
		ReflectionTestUtils.setField(externalBeerService, "getBeersEndpoint", "/beers");
	}

	@Test
	public void testGetAllBeersByParams() throws BeerCatalogApiException {
		BeerFilterDto beerFilterDto = createFilteredBeerDto(BEER_TYPE_ID, MANUFACTURER_ID);
		Pageable pageable = createPageRequestWithDefaultValues();
		List<PunkApiBeerResponseDto> punkApiBeerResponseDtoList = Collections.singletonList(createPunkApiBeerResponseDto());
		ResponseEntity<String> responseEntity = new ResponseEntity<>(new Gson().toJson(punkApiBeerResponseDtoList), HttpStatus.OK);
		when(httpClient.get(PUNK_API_BEERS_MOCK_URL)).thenReturn(responseEntity);
		List<BeerDto> beerDtoList = Collections.singletonList(createBeerDto(BEER_ID, BEER_TYPE_ID, MANUFACTURER_ID));
		when(punkApiBeerResponseMapper.punkApiBeerResponseListDtoToBeerListDto(punkApiBeerResponseDtoList)).thenReturn(beerDtoList);
		Page<BeerDto> expectedBeerDtoPage = createBeerDtoPage(BEER_ID);
		assertEquals(expectedBeerDtoPage, externalBeerService.getAllBeersByParams(beerFilterDto, pageable));
	}

}
