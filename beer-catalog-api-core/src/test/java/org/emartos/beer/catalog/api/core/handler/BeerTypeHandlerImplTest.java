package org.emartos.beer.catalog.api.core.handler;

import org.emartos.beer.catalog.api.core.exception.BadRequestException;
import org.emartos.beer.catalog.api.core.exception.NotFoundException;
import org.emartos.beer.catalog.api.core.handler.impl.BeerTypeHandlerImpl;
import org.emartos.beer.catalog.api.core.service.BeerTypeService;
import org.emartos.beer.catalog.api.repository.model.BeerTypeDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import static org.emartos.beer.catalog.api.core.BeerCatalogApiCoreDataFactory.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class BeerTypeHandlerImplTest {

	@Mock
	private BeerTypeService beerTypeService;

	private BeerTypeHandler beerTypeHandler;

	@Before
	public void init() {
		beerTypeHandler = new BeerTypeHandlerImpl(beerTypeService);
	}

	@Test
	public void testCreateBeerType() throws BadRequestException {
		// Invalid BeerType due to Null params
		BeerTypeDto beerDto = createBeerTypeDto(BEER_TYPE_ID);
		beerDto.setName(null);
		assertThrows(BadRequestException.class, () -> beerTypeHandler.createBeerType(beerDto));

		// Valid BeerType
		BeerTypeDto validBeerTypeDto = createBeerTypeDto(BEER_TYPE_ID);
		when(beerTypeService.createBeerType(validBeerTypeDto)).thenReturn(validBeerTypeDto);
		assertEquals(validBeerTypeDto, beerTypeHandler.createBeerType(validBeerTypeDto));
	}

	@Test
	public void testGetAllBeerTypes() {
		// Non-Empty result in our DB
		Page<BeerTypeDto> beerTypeDtoPage = createBeerTypeDtoPage(BEER_ID);
		when(beerTypeService.getAllBeerTypes(createPageRequestWithDefaultValues())).thenReturn(beerTypeDtoPage);
		assertEquals(beerTypeDtoPage, beerTypeHandler.getAllBeerTypes(createPageRequestWithDefaultValues()));

	}

	@Test
	public void testGetBeerTypeById() throws NotFoundException {
		BeerTypeDto beerTypeDto = createBeerTypeDto(BEER_TYPE_ID);
		when(beerTypeService.getBeerTypeById(BEER_ID)).thenReturn(beerTypeDto);
		assertEquals(beerTypeDto, beerTypeHandler.getBeerTypeById(BEER_ID));
	}

	@Test
	public void testUpdateBeerType() throws NotFoundException, BadRequestException {
		BeerTypeDto beerTypeDto = createBeerTypeDto(BEER_TYPE_ID);
		when(beerTypeService.updateBeerType(beerTypeDto)).thenReturn(beerTypeDto);
		assertEquals(beerTypeDto, beerTypeHandler.updateBeerType(beerTypeDto));
	}

	@Test
	public void testDeleteBeerTypeById() throws NotFoundException {
		when(beerTypeService.deleteBeerTypeById(BEER_ID)).thenReturn(true);
		assertTrue(beerTypeHandler.deleteBeerTypeById(BEER_ID));
	}

}
