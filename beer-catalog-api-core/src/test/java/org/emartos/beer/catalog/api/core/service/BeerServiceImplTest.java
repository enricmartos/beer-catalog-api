package org.emartos.beer.catalog.api.core.service;

import org.emartos.beer.catalog.api.core.exception.NotFoundException;
import org.emartos.beer.catalog.api.core.service.impl.BeerServiceImpl;
import org.emartos.beer.catalog.api.repository.model.BeerDto;
import org.emartos.beer.catalog.api.repository.repository.BeerRepository;
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
public class BeerServiceImplTest {

	@Mock
	private BeerRepository beerRepository;

	private BeerService beerService;

	@Before
	public void init() {
		beerService = new BeerServiceImpl(beerRepository);
	}

	@Test
	public void testCreateBeer() {
		BeerDto beerDto = createBeerDto(BEER_ID, BEER_TYPE_ID, MANUFACTURER_ID);
		when(beerRepository.create(beerDto)).thenReturn(beerDto);
		assertEquals(beerDto, beerService.createBeer(beerDto));
	}

	@Test
	public void testGetAllBeersByParams() {
		Page<BeerDto> beerDtoPage = createBeerDtoPage(BEER_ID);
		when(beerRepository.getAllByParams(createFilteredBeerDto(BEER_TYPE_ID, MANUFACTURER_ID), createPageRequestWithDefaultValues())).thenReturn(beerDtoPage);
		assertEquals(beerDtoPage, beerService.getAllBeersByParams(createFilteredBeerDto(BEER_TYPE_ID, MANUFACTURER_ID), createPageRequestWithDefaultValues()));
	}

	@Test
	public void testGetBeerById() throws NotFoundException {
		// BeerDto exists
		Optional<BeerDto> beerDto = Optional.of(createBeerDto(BEER_ID, BEER_TYPE_ID, MANUFACTURER_ID));
		when(beerRepository.getById(BEER_ID)).thenReturn(beerDto);
		assertEquals(beerDto.get(), beerService.getBeerById(BEER_ID));

		// BeerDto does not exist
		when(beerRepository.getById(NON_EXISTENT_BEER_ID)).thenReturn(Optional.ofNullable(null));
		assertThrows(NotFoundException.class, () -> beerService.getBeerById(NON_EXISTENT_BEER_ID));
	}

	@Test
	public void testUpdateBeer() throws NotFoundException {
		// BeerDto exists
		Optional<BeerDto> beerDto = Optional.of(createBeerDto(BEER_ID, BEER_TYPE_ID, MANUFACTURER_ID));
		when(beerRepository.getById(BEER_ID)).thenReturn(beerDto);
		when(beerRepository.update(beerDto.get())).thenReturn(beerDto.get());
		assertEquals(beerDto.get(), beerService.updateBeer(beerDto.get()));

		// BeerDto does not exist
		when(beerRepository.getById(NON_EXISTENT_BEER_ID)).thenReturn(Optional.empty());
		BeerDto nonexistentBeer = createBeerDto(NON_EXISTENT_BEER_ID, NON_EXISTENT_BEER_TYPE_ID, NON_EXISTENT_MANUFACTURER_ID);
		assertThrows(NotFoundException.class, () -> beerService.updateBeer(nonexistentBeer));
	}

	@Test
	public void testDeleteBeerById() throws NotFoundException {
		// BeerDto exists
		Optional<BeerDto> beerDto = Optional.of(createBeerDto(BEER_ID, BEER_TYPE_ID, MANUFACTURER_ID));
		when(beerRepository.getById(BEER_ID)).thenReturn(beerDto);
		when(beerRepository.deleteById(BEER_ID)).thenReturn(true);
		assertTrue(beerService.deleteBeerById(BEER_ID));

		// BeerDto does not exist
		when(beerRepository.getById(NON_EXISTENT_BEER_ID)).thenReturn(Optional.empty());
		assertThrows(NotFoundException.class, () -> beerService.deleteBeerById(NON_EXISTENT_BEER_ID));
	}

}
