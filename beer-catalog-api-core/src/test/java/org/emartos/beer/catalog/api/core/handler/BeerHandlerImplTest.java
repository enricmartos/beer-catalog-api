package org.emartos.beer.catalog.api.core.handler;

import org.emartos.beer.catalog.api.core.exception.BadRequestException;
import org.emartos.beer.catalog.api.core.exception.BeerCatalogApiException;
import org.emartos.beer.catalog.api.core.exception.NotFoundException;
import org.emartos.beer.catalog.api.core.handler.impl.BeerHandlerImpl;
import org.emartos.beer.catalog.api.core.service.BeerService;
import org.emartos.beer.catalog.api.core.service.BeerTypeService;
import org.emartos.beer.catalog.api.core.service.ExternalBeerService;
import org.emartos.beer.catalog.api.core.service.ManufacturerService;
import org.emartos.beer.catalog.api.repository.model.BeerDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.emartos.beer.catalog.api.core.BeerCatalogApiCoreTestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class BeerHandlerImplTest {

	@Mock
	private BeerService beerService;

	@Mock
	private ExternalBeerService externalBeerService;

	@Mock
	private ManufacturerService manufacturerService;

	@Mock
	private BeerTypeService beerTypeService;

	private BeerHandler beerHandler;

	@Before
	public void init() {
		beerHandler = new BeerHandlerImpl(beerService, externalBeerService,
				manufacturerService, beerTypeService);
	}

	@Test
	public void testCreateBeer() throws NotFoundException, BadRequestException {
		// Invalid Beer due to Null params (at least one)
		BeerDto beerDto = getBeerDto(BEER_ID, BEER_TYPE_ID, MANUFACTURER_ID);
		beerDto.setName(null);
		assertThrows(BadRequestException.class, () -> beerHandler.createBeer(beerDto));

		// Invalid Beer due to Beer Type does not exist
		BeerDto beerDtoWithNonExistentBeerType = getBeerDto(BEER_ID, NON_EXISTENT_BEER_TYPE_ID, MANUFACTURER_ID);
		when(beerTypeService.getBeerTypeById(NON_EXISTENT_BEER_TYPE_ID)).thenThrow(NotFoundException.class);
		assertThrows(BadRequestException.class, () -> beerHandler.createBeer(beerDtoWithNonExistentBeerType));

		// Invalid Beer due to Manufacturer does not exist
		BeerDto beerDtoWithNonExistentManufacturer = getBeerDto(BEER_ID, BEER_TYPE_ID, NON_EXISTENT_MANUFACTURER_ID);
		when(manufacturerService.getManufacturerById(NON_EXISTENT_MANUFACTURER_ID)).thenThrow(NotFoundException.class);
		assertThrows(BadRequestException.class, () -> beerHandler.createBeer(beerDtoWithNonExistentManufacturer));

		// Valid Beer
		BeerDto validBeerDto = getBeerDto(BEER_ID, BEER_TYPE_ID, MANUFACTURER_ID);
		when(beerTypeService.getBeerTypeById(BEER_TYPE_ID)).thenReturn(getBeerTypeDto(BEER_TYPE_ID));
		when(manufacturerService.getManufacturerById(MANUFACTURER_ID)).thenReturn(getManufacturerDto(MANUFACTURER_ID));
		when(beerService.createBeer(validBeerDto)).thenReturn(validBeerDto);
		assertEquals(validBeerDto, beerHandler.createBeer(validBeerDto));
	}

	@Test
	public void testGetAllBeers() {
		Page<BeerDto> beerDtoPage = getBeerDtoPage(BEER_ID);
		when(beerService.getAllBeers(getPageRequestWithDefaultValues())).thenReturn(beerDtoPage);
		assertEquals(beerDtoPage, beerHandler.getAllBeers(getPageRequestWithDefaultValues()));
	}

	@Test
	public void testGetAllBeersByName() throws BeerCatalogApiException {
		// Non-Empty result in our DB
		Page<BeerDto> beerDtoPage = getBeerDtoPage(BEER_ID);
		when(beerService.getAllBeersByName(BEER_NAME, getPageRequestWithDefaultValues())).thenReturn(beerDtoPage);
		assertEquals(beerDtoPage, beerHandler.getAllBeersByName(BEER_NAME, getPageRequestWithDefaultValues()));

		// Empty result in our DB
		when(beerService.getAllBeersByName(NON_EXISTENT_BEER_NAME, getPageRequestWithDefaultValues())).thenReturn(new PageImpl<>(Collections.emptyList()));
		when(externalBeerService.getAllBeersByName(NON_EXISTENT_BEER_NAME, getPageRequestWithDefaultValues())).thenReturn(beerDtoPage);
		assertEquals(beerDtoPage, beerHandler.getAllBeersByName(NON_EXISTENT_BEER_NAME, getPageRequestWithDefaultValues()));
	}

	@Test
	public void testGetBeerById() throws NotFoundException {
		BeerDto beerDto = getBeerDto(BEER_ID, BEER_TYPE_ID, MANUFACTURER_ID);
		when(beerService.getBeerById(BEER_ID)).thenReturn(beerDto);
		assertEquals(beerDto, beerHandler.getBeerById(BEER_ID));
	}

	@Test
	public void testUpdateBeer() throws NotFoundException, BadRequestException {
		BeerDto validBeerDto = getBeerDto(BEER_ID, BEER_TYPE_ID, MANUFACTURER_ID);
		when(beerTypeService.getBeerTypeById(BEER_TYPE_ID)).thenReturn(getBeerTypeDto(BEER_TYPE_ID));
		when(manufacturerService.getManufacturerById(MANUFACTURER_ID)).thenReturn(getManufacturerDto(MANUFACTURER_ID));
		when(beerService.updateBeer(validBeerDto)).thenReturn(validBeerDto);
		assertEquals(validBeerDto, beerHandler.updateBeer(validBeerDto));
	}

	@Test
	public void testDeleteBeerById() throws NotFoundException {
		when(beerService.deleteBeerById(BEER_ID)).thenReturn(true);
		assertTrue(beerHandler.deleteBeerById(BEER_ID));
	}

}
