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

import static org.emartos.beer.catalog.api.core.BeerCatalogApiCoreDataFactory.*;
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
		BeerDto beerDto = createBeerDto(BEER_ID, BEER_TYPE_ID, MANUFACTURER_ID);
		beerDto.setName(null);
		assertThrows(BadRequestException.class, () -> beerHandler.createBeer(beerDto));

		// Invalid Beer due to Beer Type does not exist
		BeerDto beerDtoWithNonExistentBeerType = createBeerDto(BEER_ID, NON_EXISTENT_BEER_TYPE_ID, MANUFACTURER_ID);
		when(beerTypeService.getBeerTypeById(NON_EXISTENT_BEER_TYPE_ID)).thenThrow(NotFoundException.class);
		assertThrows(BadRequestException.class, () -> beerHandler.createBeer(beerDtoWithNonExistentBeerType));

		// Invalid Beer due to Manufacturer does not exist
		BeerDto beerDtoWithNonExistentManufacturer = createBeerDto(BEER_ID, BEER_TYPE_ID, NON_EXISTENT_MANUFACTURER_ID);
		when(manufacturerService.getManufacturerById(NON_EXISTENT_MANUFACTURER_ID)).thenThrow(NotFoundException.class);
		assertThrows(BadRequestException.class, () -> beerHandler.createBeer(beerDtoWithNonExistentManufacturer));

		// Valid Beer
		BeerDto validBeerDto = createBeerDto(BEER_ID, BEER_TYPE_ID, MANUFACTURER_ID);
		when(beerTypeService.getBeerTypeById(BEER_TYPE_ID)).thenReturn(createBeerTypeDto(BEER_TYPE_ID));
		when(manufacturerService.getManufacturerById(MANUFACTURER_ID)).thenReturn(createManufacturerDto(MANUFACTURER_ID));
		when(beerService.createBeer(validBeerDto)).thenReturn(validBeerDto);
		assertEquals(validBeerDto, beerHandler.createBeer(validBeerDto));
	}

	@Test
	public void testGetAllBeersByParams() throws BeerCatalogApiException {
		// Non-Empty result in our DB
		Page<BeerDto> beerDtoPage = createBeerDtoPage(BEER_ID);
		when(beerService.getAllBeersByParams(createFilteredBeerDto(BEER_ID, BEER_TYPE_ID, MANUFACTURER_ID), createPageRequestWithDefaultValues())).thenReturn(beerDtoPage);
		assertEquals(beerDtoPage, beerHandler.getAllBeersByParams(createFilteredBeerDto(BEER_ID, BEER_TYPE_ID, MANUFACTURER_ID), createPageRequestWithDefaultValues()));

		// Empty result in our DB
		when(beerService.getAllBeersByParams(createFilteredBeerDto(NON_EXISTENT_BEER_ID, BEER_TYPE_ID, MANUFACTURER_ID),
				createPageRequestWithDefaultValues())).thenReturn(new PageImpl<>(Collections.emptyList()));
		when(externalBeerService.getAllBeersByParams(createFilteredBeerDto(NON_EXISTENT_BEER_ID, BEER_TYPE_ID, MANUFACTURER_ID),
				createPageRequestWithDefaultValues())).thenReturn(beerDtoPage);
		assertEquals(beerDtoPage, beerHandler.getAllBeersByParams(createFilteredBeerDto(NON_EXISTENT_BEER_ID, BEER_TYPE_ID, MANUFACTURER_ID),
				createPageRequestWithDefaultValues()));
	}

	@Test
	public void testGetBeerById() throws NotFoundException {
		BeerDto beerDto = createBeerDto(BEER_ID, BEER_TYPE_ID, MANUFACTURER_ID);
		when(beerService.getBeerById(BEER_ID)).thenReturn(beerDto);
		assertEquals(beerDto, beerHandler.getBeerById(BEER_ID));
	}

	@Test
	public void testUpdateBeer() throws NotFoundException, BadRequestException {
		BeerDto validBeerDto = createBeerDto(BEER_ID, BEER_TYPE_ID, MANUFACTURER_ID);
		when(beerTypeService.getBeerTypeById(BEER_TYPE_ID)).thenReturn(createBeerTypeDto(BEER_TYPE_ID));
		when(manufacturerService.getManufacturerById(MANUFACTURER_ID)).thenReturn(createManufacturerDto(MANUFACTURER_ID));
		when(beerService.updateBeer(validBeerDto)).thenReturn(validBeerDto);
		assertEquals(validBeerDto, beerHandler.updateBeer(validBeerDto));
	}

	@Test
	public void testDeleteBeerById() throws NotFoundException {
		when(beerService.deleteBeerById(BEER_ID)).thenReturn(true);
		assertTrue(beerHandler.deleteBeerById(BEER_ID));
	}

}
