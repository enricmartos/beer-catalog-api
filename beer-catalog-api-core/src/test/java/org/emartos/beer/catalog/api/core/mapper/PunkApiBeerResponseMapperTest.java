package org.emartos.beer.catalog.api.core.mapper;

import org.emartos.beer.catalog.api.core.dto.punkapi.PunkApiBeerResponseDto;
import org.emartos.beer.catalog.api.repository.model.BeerDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.emartos.beer.catalog.api.core.BeerCatalogApiCoreDataFactory.createBeerFromPunkApiResponseDto;
import static org.emartos.beer.catalog.api.core.BeerCatalogApiCoreDataFactory.createPunkApiBeerResponseDto;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
public class PunkApiBeerResponseMapperTest {

	private PunkApiBeerResponseMapper punkApiBeerResponseMapper;

	@Before
	public void setup() {
		punkApiBeerResponseMapper = new PunkApiBeerResponseMapper();
	}

	@Test
	public void testPunkApiBeerResponseDtoToBeerDto() {
		// punkApiBeerResponseDto null
		assertNull(punkApiBeerResponseMapper.punkApiBeerResponseDtoToBeerDto(null));

		// punkApiBeerResponseDto not null
		PunkApiBeerResponseDto punkApiBeerResponseDto = createPunkApiBeerResponseDto();
		BeerDto beerDto = punkApiBeerResponseMapper.punkApiBeerResponseDtoToBeerDto(punkApiBeerResponseDto);
		BeerDto expectedBeerDto = createBeerFromPunkApiResponseDto();
		assertEquals(expectedBeerDto, beerDto);
	}

	@Test
	public void testPunkApiBeerResponseListDtoToBeerListDto() {
		// punkApiBeerListResponseListDto null
		assertEquals(new ArrayList<>(), punkApiBeerResponseMapper.punkApiBeerResponseListDtoToBeerListDto(null));
		// punkApiBeerListResponseListDto empty
		assertEquals(new ArrayList<>(), punkApiBeerResponseMapper.punkApiBeerResponseListDtoToBeerListDto(new ArrayList<>()));

		// punkApiBeerListResponseListDto not empty
		List<PunkApiBeerResponseDto> punkApiBeerResponseDtoList = Collections.singletonList(createPunkApiBeerResponseDto());
		List<BeerDto> expectedBeerDtoList = Collections.singletonList(createBeerFromPunkApiResponseDto());
		assertEquals(expectedBeerDtoList, punkApiBeerResponseMapper.punkApiBeerResponseListDtoToBeerListDto(punkApiBeerResponseDtoList));
	}

}
