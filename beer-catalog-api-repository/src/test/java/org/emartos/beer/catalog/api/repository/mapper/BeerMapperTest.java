package org.emartos.beer.catalog.api.repository.mapper;

import org.emartos.beer.catalog.api.repository.entity.Beer;
import org.emartos.beer.catalog.api.repository.model.BeerDto;
import org.emartos.beer.catalog.api.repository.repository.BeerTypeRepository;
import org.emartos.beer.catalog.api.repository.repository.ManufacturerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.emartos.beer.catalog.api.repository.BeerManagerApiJpaDataFactory.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class BeerMapperTest {

	@Mock
	private BeerTypeRepository beerTypeRepository;

	@Mock
	private ManufacturerRepository manufacturerRepository;
	
	private BeerMapper beerMapper;

	@Before
	public void setup() {
		beerMapper = new BeerMapper(beerTypeRepository, manufacturerRepository);
	}

	@Test
	public void testBeerDtoToBeer() {
		// beerDto null
		assertNull(beerMapper.beerDtoToBeer(null));

		// beerDto not null
		BeerDto beerDto = getBeerDto(BEER_ID, BEER_TYPE_ID, MANUFACTURER_ID);
		Beer beer = beerMapper.beerDtoToBeer(beerDto);
		Beer expectedBeer = getBeer(BEER_ID, BEER_TYPE_ID, MANUFACTURER_ID);
		assertEquals(expectedBeer, beer);
	}

	@Test
	public void testBeerToBeerDto() {
		// beer null
		assertNull(beerMapper.beerToBeerDto(null));
		// beer id null
		assertNull(beerMapper.beerToBeerDto(Beer.builder().build()));

		// beer not null
		when(beerTypeRepository.getById(BEER_TYPE_ID)).thenReturn(Optional.of(getBeerTypeDto(BEER_TYPE_ID)));
		when(manufacturerRepository.getById(MANUFACTURER_ID)).thenReturn(Optional.of(getManufacturerDto(MANUFACTURER_ID)));
		Beer beer = getBeer(BEER_ID, BEER_TYPE_ID, MANUFACTURER_ID);
		BeerDto expectedBeerDto = getBeerDto(BEER_ID, BEER_TYPE_ID, MANUFACTURER_ID);
		assertEquals(expectedBeerDto, beerMapper.beerToBeerDto(beer));
	}

	@Test
	public void testBeerListToBeerDtoList() {
		// beerList null
		assertEquals(new ArrayList<>(), beerMapper.beerListToBeerDtoList(null));
		// beerList empty
		assertEquals(new ArrayList<>(), beerMapper.beerListToBeerDtoList(new ArrayList<>()));

		// beerList not empty
		when(beerTypeRepository.getById(BEER_TYPE_ID)).thenReturn(Optional.of(getBeerTypeDto(BEER_TYPE_ID)));
		when(manufacturerRepository.getById(MANUFACTURER_ID)).thenReturn(Optional.of(getManufacturerDto(MANUFACTURER_ID)));
		List<Beer> recipeList = Collections.singletonList(getBeer(BEER_ID, BEER_TYPE_ID, MANUFACTURER_ID));
		List<BeerDto> expectedBeerDtoList = Collections.singletonList(getBeerDto(BEER_ID, BEER_TYPE_ID, MANUFACTURER_ID));
		assertEquals(expectedBeerDtoList, beerMapper.beerListToBeerDtoList(recipeList));
	}
	
}
