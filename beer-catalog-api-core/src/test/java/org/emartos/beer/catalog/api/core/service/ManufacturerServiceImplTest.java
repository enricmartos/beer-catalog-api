package org.emartos.beer.catalog.api.core.service;

import org.emartos.beer.catalog.api.core.exception.NotFoundException;
import org.emartos.beer.catalog.api.core.service.impl.ManufacturerServiceImpl;
import org.emartos.beer.catalog.api.repository.model.ManufacturerDto;
import org.emartos.beer.catalog.api.repository.repository.ManufacturerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.emartos.beer.catalog.api.core.BeerCatalogApiCoreDataFactory.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ManufacturerServiceImplTest {

	@Mock
	private ManufacturerRepository manufacturerRepository;

	private ManufacturerService manufacturerService;

	@Before
	public void init() {
		manufacturerService = new ManufacturerServiceImpl(manufacturerRepository);
	}

	@Test
	public void testCreateManufacturer() {
		ManufacturerDto beerDto = createManufacturerDto(BEER_TYPE_ID);
		when(manufacturerRepository.create(beerDto)).thenReturn(beerDto);
		assertEquals(beerDto, manufacturerService.createManufacturer(beerDto));
	}

	@Test
	public void testGetAllManufacturers() {
		Page<ManufacturerDto> beerDtoPage = createManufacturerDtoPage(BEER_TYPE_ID);
		when(manufacturerRepository.getAll(createPageRequestWithDefaultValues())).thenReturn(beerDtoPage);
		assertEquals(beerDtoPage, manufacturerService.getAllManufacturers(createPageRequestWithDefaultValues()));
	}

	@Test
	public void testGetManufacturerById() throws NotFoundException {
		// ManufacturerDto exists
		Optional<ManufacturerDto> beerDto = Optional.of(createManufacturerDto(BEER_TYPE_ID));
		when(manufacturerRepository.getById(BEER_TYPE_ID)).thenReturn(beerDto);
		assertEquals(beerDto.get(), manufacturerService.getManufacturerById(BEER_TYPE_ID));

		// ManufacturerDto does not exist
		when(manufacturerRepository.getById(NON_EXISTENT_BEER_TYPE_ID)).thenReturn(Optional.ofNullable(null));
		assertThrows(NotFoundException.class, () -> manufacturerService.getManufacturerById(NON_EXISTENT_BEER_TYPE_ID));
	}

	@Test
	public void testUpdateManufacturer() throws NotFoundException {
		// ManufacturerDto exists
		Optional<ManufacturerDto> beerDto = Optional.of(createManufacturerDto(BEER_TYPE_ID));
		when(manufacturerRepository.getById(BEER_TYPE_ID)).thenReturn(beerDto);
		when(manufacturerRepository.update(beerDto.get())).thenReturn(beerDto.get());
		assertEquals(beerDto.get(), manufacturerService.updateManufacturer(beerDto.get()));

		// ManufacturerDto does not exist
		when(manufacturerRepository.getById(NON_EXISTENT_BEER_TYPE_ID)).thenReturn(Optional.empty());
		ManufacturerDto nonexistentManufacturer = createManufacturerDto(NON_EXISTENT_BEER_TYPE_ID);
		assertThrows(NotFoundException.class, () -> manufacturerService.updateManufacturer(nonexistentManufacturer));
	}

	@Test
	public void testDeleteManufacturerById() throws NotFoundException {
		// ManufacturerDto exists
		Optional<ManufacturerDto> beerDto = Optional.of(createManufacturerDto(BEER_TYPE_ID));
		when(manufacturerRepository.getById(BEER_TYPE_ID)).thenReturn(beerDto);
		when(manufacturerRepository.deleteById(BEER_TYPE_ID)).thenReturn(true);
		assertTrue(manufacturerService.deleteManufacturerById(BEER_TYPE_ID));

		// ManufacturerDto does not exist
		when(manufacturerRepository.getById(NON_EXISTENT_BEER_TYPE_ID)).thenReturn(Optional.empty());
		assertThrows(NotFoundException.class, () -> manufacturerService.deleteManufacturerById(NON_EXISTENT_BEER_TYPE_ID));
	}

}
