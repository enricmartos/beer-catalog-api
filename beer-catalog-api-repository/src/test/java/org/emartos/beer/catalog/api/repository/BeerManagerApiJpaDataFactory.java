package org.emartos.beer.catalog.api.repository;

import org.emartos.beer.catalog.api.repository.entity.Beer;
import org.emartos.beer.catalog.api.repository.entity.BeerType;
import org.emartos.beer.catalog.api.repository.entity.Manufacturer;
import org.emartos.beer.catalog.api.repository.model.BeerDto;
import org.emartos.beer.catalog.api.repository.model.BeerTypeDto;
import org.emartos.beer.catalog.api.repository.model.ManufacturerDto;

public class BeerManagerApiJpaDataFactory {

	public static final String BEER_NAME = "Beer name";
	public static final Long BEER_ID = 1L;
	public static final Long BEER_TYPE_ID = 3L;
	public static final Long MANUFACTURER_ID = 5L;

	private BeerManagerApiJpaDataFactory() {
		// Default constructor
	}

	// region Beer

	public static BeerDto createBeerDto(Long id, Long beerTypeId, Long manufacturerId) {
		return BeerDto.builder()
					  .id(id)
					  .name(BEER_NAME)
					  .description("Beer description")
					  .graduation(4.6F)
					  .beerTypeId(beerTypeId)
					  .manufacturerId(manufacturerId)
					  .build();
	}

	public static Beer createBeer(Long id, Long beerTypeId, Long manufacturerId) {
		return Beer.builder()
				   .id(id)
				   .name(BEER_NAME)
				   .description("Beer description")
				   .graduation(4.6F)
				   .beerType(BeerType.builder().id(beerTypeId).build())
				   .manufacturer(Manufacturer.builder().id(manufacturerId).build())
				   .build();
	}

	// endregion

	// region Beer Type

	public static BeerTypeDto createBeerTypeDto(Long id) {
		return BeerTypeDto.builder()
						  .id(id)
						  .name("Beer Type name")
						  .build();
	}

	public static BeerType createBeerType(Long id) {
		return BeerType.builder()
						  .id(id)
						  .name("Beer Type name")
						  .build();
	}

	// endregion

	// region Manufacturer

	public static ManufacturerDto createManufacturerDto(Long id) {
		return ManufacturerDto.builder()
							  .id(id)
							  .name("Manufacturer name")
							  .nationality("Nationality")
							  .build();
	}

	public static Manufacturer createManufacturer(Long id) {
		return Manufacturer.builder()
							  .id(id)
							  .name("Manufacturer name")
							  .nationality("Nationality")
							  .build();
	}

	// endregion

}
