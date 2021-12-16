package org.emartos.beer.catalog.api.repository.mapper;

import org.emartos.beer.catalog.api.repository.entity.Manufacturer;
import org.emartos.beer.catalog.api.repository.model.ManufacturerDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.emartos.beer.catalog.api.repository.BeerManagerApiJpaDataFactory.BEER_TYPE_ID;
import static org.emartos.beer.catalog.api.repository.BeerManagerApiJpaDataFactory.createManufacturer;
import static org.emartos.beer.catalog.api.repository.BeerManagerApiJpaDataFactory.createManufacturerDto;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
public class ManufacturerMapperTest {
	
	private ManufacturerMapper manufacturerMapper;

	@Before
	public void setup() {
		manufacturerMapper = new ManufacturerMapper();
	}

	@Test
	public void testManufacturerDtoToManufacturer() {
		// manufacturerDto null
		assertNull(manufacturerMapper.manufacturerDtoToManufacturer(null));

		// manufacturerDto not null
		ManufacturerDto manufacturerDto = createManufacturerDto(BEER_TYPE_ID);
		Manufacturer manufacturer = manufacturerMapper.manufacturerDtoToManufacturer(manufacturerDto);
		Manufacturer expectedManufacturer = createManufacturer(BEER_TYPE_ID);
		assertEquals(expectedManufacturer, manufacturer);
	}

	@Test
	public void testManufacturerToManufacturerDto() {
		// manufacturer null
		assertNull(manufacturerMapper.manufacturerToManufacturerDto(null));
		// manufacturer id null
		assertNull(manufacturerMapper.manufacturerToManufacturerDto(Manufacturer.builder().build()));

		// manufacturer not null
		Manufacturer manufacturer = createManufacturer(BEER_TYPE_ID);
		ManufacturerDto expectedManufacturerDto = createManufacturerDto(BEER_TYPE_ID);
		assertEquals(expectedManufacturerDto, manufacturerMapper.manufacturerToManufacturerDto(manufacturer));
	}

	@Test
	public void testManufacturerListToManufacturerDtoList() {
		// manufacturerList null
		assertEquals(new ArrayList<>(), manufacturerMapper.manufacturerListToManufacturerDtoList(null));
		// manufacturerList empty
		assertEquals(new ArrayList<>(), manufacturerMapper.manufacturerListToManufacturerDtoList(new ArrayList<>()));

		// manufacturerList not empty
		List<Manufacturer> manufacturerList = Collections.singletonList(createManufacturer(BEER_TYPE_ID));
		List<ManufacturerDto> expectedManufacturerDtoList = Collections.singletonList(createManufacturerDto(BEER_TYPE_ID));
		assertEquals(expectedManufacturerDtoList, manufacturerMapper.manufacturerListToManufacturerDtoList(manufacturerList));
	}

}
