package org.emartos.beerType.catalog.api.repository.mapper;

import org.emartos.beer.catalog.api.repository.entity.BeerType;
import org.emartos.beer.catalog.api.repository.mapper.BeerTypeMapper;
import org.emartos.beer.catalog.api.repository.model.BeerTypeDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.emartos.beer.catalog.api.repository.BeerManagerApiJpaDataFactory.BEER_TYPE_ID;
import static org.emartos.beer.catalog.api.repository.BeerManagerApiJpaDataFactory.createBeerType;
import static org.emartos.beer.catalog.api.repository.BeerManagerApiJpaDataFactory.createBeerTypeDto;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
public class BeerTypeMapperTest {

	private BeerTypeMapper beerTypeMapper;

	@Before
	public void setup() {
		beerTypeMapper = new BeerTypeMapper();
	}

	@Test
	public void testBeerTypeDtoToBeerType() {
		// beerTypeDto null
		assertNull(beerTypeMapper.beerTypeDtoToBeerType(null));

		// beerTypeDto not null
		BeerTypeDto beerTypeDto = createBeerTypeDto(BEER_TYPE_ID);
		BeerType beerType = beerTypeMapper.beerTypeDtoToBeerType(beerTypeDto);
		BeerType expectedBeerType = createBeerType(BEER_TYPE_ID);
		assertEquals(expectedBeerType, beerType);
	}

	@Test
	public void testBeerTypeToBeerTypeDto() {
		// beerType null
		assertNull(beerTypeMapper.beerTypeToBeerTypeDto(null));
		// beerType id null
		assertNull(beerTypeMapper.beerTypeToBeerTypeDto(BeerType.builder().build()));

		// beerType not null
		BeerType beerType = createBeerType(BEER_TYPE_ID);
		BeerTypeDto expectedBeerTypeDto = createBeerTypeDto(BEER_TYPE_ID);
		assertEquals(expectedBeerTypeDto, beerTypeMapper.beerTypeToBeerTypeDto(beerType));
	}

	@Test
	public void testBeerTypeListToBeerTypeDtoList() {
		// beerTypeList null
		assertEquals(new ArrayList<>(), beerTypeMapper.beerTypeListToBeerTypeDtoList(null));
		// beerTypeList empty
		assertEquals(new ArrayList<>(), beerTypeMapper.beerTypeListToBeerTypeDtoList(new ArrayList<>()));

		// beerTypeList not empty
		List<BeerType> beerTypeList = Collections.singletonList(createBeerType(BEER_TYPE_ID));
		List<BeerTypeDto> expectedBeerTypeDtoList = Collections.singletonList(createBeerTypeDto(BEER_TYPE_ID));
		assertEquals(expectedBeerTypeDtoList, beerTypeMapper.beerTypeListToBeerTypeDtoList(beerTypeList));
	}

}
