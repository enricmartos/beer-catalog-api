package org.emartos.beer.catalog.api.core.handler;

import org.emartos.beer.catalog.api.core.exception.BadRequestException;
import org.emartos.beer.catalog.api.core.exception.NotFoundException;
import org.emartos.beer.catalog.api.core.handler.impl.ManufacturerHandlerImpl;
import org.emartos.beer.catalog.api.core.service.ManufacturerService;
import org.emartos.beer.catalog.api.repository.model.ManufacturerDto;
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
public class ManufacturerHandlerImplTest {

	@Mock
	private ManufacturerService manufacturerService;

	private ManufacturerHandler manufacturerHandler;

	@Before
	public void init() {
		manufacturerHandler = new ManufacturerHandlerImpl(manufacturerService);
	}

	@Test
	public void testCreateManufacturer() throws BadRequestException {
		// Invalid Manufacturer due to Null params
		ManufacturerDto manufacturerDto = createManufacturerDto(MANUFACTURER_ID);
		manufacturerDto.setName(null);
		assertThrows(BadRequestException.class, () -> manufacturerHandler.createManufacturer(manufacturerDto));

		// Valid Manufacturer
		ManufacturerDto validManufacturerDto = createManufacturerDto(MANUFACTURER_ID);
		when(manufacturerService.createManufacturer(validManufacturerDto)).thenReturn(validManufacturerDto);
		assertEquals(validManufacturerDto, manufacturerHandler.createManufacturer(validManufacturerDto));
	}

	@Test
	public void testGetAllManufacturers() {
		// Non-Empty result in our DB
		Page<ManufacturerDto> manufacturerDtoPage = createManufacturerDtoPage(MANUFACTURER_ID);
		when(manufacturerService.getAllManufacturers(createPageRequestWithDefaultValues())).thenReturn(manufacturerDtoPage);
		assertEquals(manufacturerDtoPage, manufacturerHandler.getAllManufacturers(createPageRequestWithDefaultValues()));

	}

	@Test
	public void testGetManufacturerById() throws NotFoundException {
		ManufacturerDto manufacturerDto = createManufacturerDto(MANUFACTURER_ID);
		when(manufacturerService.getManufacturerById(BEER_ID)).thenReturn(manufacturerDto);
		assertEquals(manufacturerDto, manufacturerHandler.getManufacturerById(BEER_ID));
	}

	@Test
	public void testUpdateManufacturer() throws NotFoundException, BadRequestException {
		ManufacturerDto manufacturerDto = createManufacturerDto(MANUFACTURER_ID);
		when(manufacturerService.updateManufacturer(manufacturerDto)).thenReturn(manufacturerDto);
		assertEquals(manufacturerDto, manufacturerHandler.updateManufacturer(manufacturerDto));
	}

	@Test
	public void testDeleteManufacturerById() throws NotFoundException {
		when(manufacturerService.deleteManufacturerById(BEER_ID)).thenReturn(true);
		assertTrue(manufacturerHandler.deleteManufacturerById(BEER_ID));
	}

}
