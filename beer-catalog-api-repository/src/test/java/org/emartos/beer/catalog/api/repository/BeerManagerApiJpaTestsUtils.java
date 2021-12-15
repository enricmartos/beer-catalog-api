package org.emartos.beer.catalog.api.repository;

import org.emartos.beer.catalog.api.repository.entity.Beer;
import org.emartos.beer.catalog.api.repository.entity.BeerType;
import org.emartos.beer.catalog.api.repository.entity.Manufacturer;
import org.emartos.beer.catalog.api.repository.model.BeerDto;
import org.emartos.beer.catalog.api.repository.model.BeerTypeDto;
import org.emartos.beer.catalog.api.repository.model.ManufacturerDto;

public class BeerManagerApiJpaTestsUtils {

	public static final String BEER_NAME = "Beer name";
	public static final String NON_EXISTENT_BEER_NAME = "Non Existent Beer name";
	public static final Long BEER_ID = 1L;
	public static final Long NON_EXISTENT_BEER_ID = 2L;
	public static final Long BEER_TYPE_ID = 3L;
	public static final Long NON_EXISTENT_BEER_TYPE_ID = 4L;
	public static final Long MANUFACTURER_ID = 5L;
	public static final Long NON_EXISTENT_MANUFACTURER_ID = 6L;

	private BeerManagerApiJpaTestsUtils() {
		// Default constructor
	}

	// region Beer

	public static BeerDto getBeerDto(Long id, Long beerTypeId, Long manufacturerId) {
		return BeerDto.builder()
					  .id(id)
					  .name(BEER_NAME)
					  .description("Beer description")
					  .graduation(4.6F)
					  .beerTypeId(beerTypeId)
					  .manufacturerId(manufacturerId)
					  .build();
	}

	public static Beer getBeer(Long id, Long beerTypeId, Long manufacturerId) {
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

	public static BeerTypeDto getBeerTypeDto(Long id) {
		return BeerTypeDto.builder()
						  .id(id)
						  .name("Beer Type name")
						  .build();
	}

	// endregion

	// region Manufacturer

	public static ManufacturerDto getManufacturerDto(Long id) {
		return ManufacturerDto.builder()
							  .id(id)
							  .name("Manufacturer name")
							  .nationality("Nationality")
							  .build();
	}

	// endregion

}
