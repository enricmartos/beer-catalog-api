package org.emartos.beer.catalog.api.core.service;

import org.emartos.beer.catalog.api.core.exception.NotFoundException;
import org.emartos.beer.catalog.api.core.service.impl.BeerTypeServiceImpl;
import org.emartos.beer.catalog.api.repository.model.BeerTypeDto;
import org.emartos.beer.catalog.api.repository.repository.BeerTypeRepository;
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
public class BeerTypeServiceImplTest {

	@Mock
	private BeerTypeRepository beerTypeRepository;

	private BeerTypeService beerTypeService;

	@Before
	public void init() {
		beerTypeService = new BeerTypeServiceImpl(beerTypeRepository);
	}

	@Test
	public void testCreateBeerType() {
		BeerTypeDto beerDto = createBeerTypeDto(BEER_TYPE_ID);
		when(beerTypeRepository.create(beerDto)).thenReturn(beerDto);
		assertEquals(beerDto, beerTypeService.createBeerType(beerDto));
	}

	@Test
	public void testGetAllBeerTypes() {
		Page<BeerTypeDto> beerDtoPage = createBeerTypeDtoPage(BEER_TYPE_ID);
		when(beerTypeRepository.getAll(createPageRequestWithDefaultValues())).thenReturn(beerDtoPage);
		assertEquals(beerDtoPage, beerTypeService.getAllBeerTypes(createPageRequestWithDefaultValues()));
	}

	@Test
	public void testGetBeerTypeById() throws NotFoundException {
		// BeerTypeDto exists
		Optional<BeerTypeDto> beerDto = Optional.of(createBeerTypeDto(BEER_TYPE_ID));
		when(beerTypeRepository.getById(BEER_TYPE_ID)).thenReturn(beerDto);
		assertEquals(beerDto.get(), beerTypeService.getBeerTypeById(BEER_TYPE_ID));

		// BeerTypeDto does not exist
		when(beerTypeRepository.getById(NON_EXISTENT_BEER_TYPE_ID)).thenReturn(Optional.ofNullable(null));
		assertThrows(NotFoundException.class, () -> beerTypeService.getBeerTypeById(NON_EXISTENT_BEER_TYPE_ID));
	}

	@Test
	public void testUpdateBeerType() throws NotFoundException {
		// BeerTypeDto exists
		Optional<BeerTypeDto> beerDto = Optional.of(createBeerTypeDto(BEER_TYPE_ID));
		when(beerTypeRepository.getById(BEER_TYPE_ID)).thenReturn(beerDto);
		when(beerTypeRepository.update(beerDto.get())).thenReturn(beerDto.get());
		assertEquals(beerDto.get(), beerTypeService.updateBeerType(beerDto.get()));

		// BeerTypeDto does not exist
		when(beerTypeRepository.getById(NON_EXISTENT_BEER_TYPE_ID)).thenReturn(Optional.empty());
		BeerTypeDto nonexistentBeerType = createBeerTypeDto(NON_EXISTENT_BEER_TYPE_ID);
		assertThrows(NotFoundException.class, () -> beerTypeService.updateBeerType(nonexistentBeerType));
	}

	@Test
	public void testDeleteBeerTypeById() throws NotFoundException {
		// BeerTypeDto exists
		Optional<BeerTypeDto> beerDto = Optional.of(createBeerTypeDto(BEER_TYPE_ID));
		when(beerTypeRepository.getById(BEER_TYPE_ID)).thenReturn(beerDto);
		when(beerTypeRepository.deleteById(BEER_TYPE_ID)).thenReturn(true);
		assertTrue(beerTypeService.deleteBeerTypeById(BEER_TYPE_ID));

		// BeerTypeDto does not exist
		when(beerTypeRepository.getById(NON_EXISTENT_BEER_TYPE_ID)).thenReturn(Optional.empty());
		assertThrows(NotFoundException.class, () -> beerTypeService.deleteBeerTypeById(NON_EXISTENT_BEER_TYPE_ID));
	}

}
