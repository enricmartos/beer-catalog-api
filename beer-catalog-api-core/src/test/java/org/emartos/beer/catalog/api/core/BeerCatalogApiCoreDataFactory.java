package org.emartos.beer.catalog.api.core;

import org.emartos.beer.catalog.api.core.dto.PageableResponseDto;
import org.emartos.beer.catalog.api.core.dto.punkapi.PunkApiBeerResponseDto;
import org.emartos.beer.catalog.api.repository.model.BeerDto;
import org.emartos.beer.catalog.api.repository.model.BeerFilterDto;
import org.emartos.beer.catalog.api.repository.model.BeerTypeDto;
import org.emartos.beer.catalog.api.repository.model.ManufacturerDto;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BeerCatalogApiCoreDataFactory {

	public static final String BEER_NAME = "Beer name";
	public static final Long BEER_ID = 1L;
	public static final Long NON_EXISTENT_BEER_ID = 2L;
	public static final Long BEER_TYPE_ID = 1L;
	public static final Long NON_EXISTENT_BEER_TYPE_ID = 2L;
	public static final Long MANUFACTURER_ID = 1L;
	public static final Long NON_EXISTENT_MANUFACTURER_ID = 2L;
	public static final Integer DEFAULT_CURRENT_PAGE = 0;
	public static final Integer DEFAULT_PAGE_SIZE = 25;

	private BeerCatalogApiCoreDataFactory() {
		// Private default constructor
	}

	// region Punk Api

	public static PunkApiBeerResponseDto createPunkApiBeerResponseDto() {
		return PunkApiBeerResponseDto.builder()
									 .id("3")
									 .name("Berliner Weisse With Yuzu - B-Sides")
									 .description("Japanese citrus fruit intensifies the sour nature of this German classic.")
									 .graduation("4.2")
									 .beerType("Japanese Citrus Berliner Weisse.")
									 .build();
	}

	// endregion

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

	public static Page<BeerDto> createBeerDtoPage(Long id) {
		List<BeerDto> beerDtoList = Collections.singletonList(createBeerDto(id, BEER_TYPE_ID, MANUFACTURER_ID));
		Pageable pageable = PageRequest.of(DEFAULT_CURRENT_PAGE, DEFAULT_PAGE_SIZE);
		return new PageImpl<>(beerDtoList, pageable, 1);
	}

	public static PageableResponseDto<BeerDto> createBeerPageableResponseDto(List<BeerDto> beerDtoList,
																			 int currentPage,
																			 int pageSize,
																			 int totalPages) {
		return new PageableResponseDto<>(beerDtoList, currentPage, pageSize, totalPages);
	}

	public static BeerFilterDto createFilteredBeerDto(Long beerTypeId, Long manufacturerId) {
		return BeerFilterDto.builder()
							.name(BEER_NAME)
							.description("Japanese citrus fruit intensifies the sour nature of this German classic.")
							.minGraduation(4F)
							.maxGraduation(5F)
							.beerTypeId(beerTypeId)
							.manufacturerId(manufacturerId)
							.build();
	}

	public static BeerDto createBeerFromPunkApiResponseDto() {
		return BeerDto.builder()
							.name("Berliner Weisse With Yuzu - B-Sides")
							.description("Japanese citrus fruit intensifies the sour nature of this German classic.")
							.graduation(4.2F)
							.externalBeerType("Japanese Citrus Berliner Weisse.")
							.externalId(3L)
							.manufacturerId(MANUFACTURER_ID)
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

	public static Page<BeerTypeDto> createBeerTypeDtoPage(Long id) {
		List<BeerTypeDto> beerTypeDtoList = Collections.singletonList(createBeerTypeDto(id));
		Pageable pageable = PageRequest.of(DEFAULT_CURRENT_PAGE, DEFAULT_PAGE_SIZE);
		return new PageImpl<>(beerTypeDtoList, pageable, 1);
	}

	// endregion

	// region Manufacturer

	public static ManufacturerDto createManufacturerDto(Long id) {
		return ManufacturerDto.builder()
							  .id(id)
							  .name("Beer Type name")
							  .nationality("Nationality")
							  .build();
	}

	public static Page<ManufacturerDto> createManufacturerDtoPage(Long id) {
		List<ManufacturerDto> manufacturerDtoList = Collections.singletonList(createManufacturerDto(id));
		Pageable pageable = PageRequest.of(DEFAULT_CURRENT_PAGE, DEFAULT_PAGE_SIZE);
		return new PageImpl<>(manufacturerDtoList, pageable, 1);
	}

	// endregion

	// region Page Request

	public static Pageable createPageRequestWithSingleSortParamAsc() {
		return PageRequest.of(DEFAULT_CURRENT_PAGE, DEFAULT_PAGE_SIZE, Sort.by(Collections.singletonList(new Sort.Order(Sort.Direction.ASC, "id"))));
	}

	public static Pageable createPageRequestWithSingleSortParamDesc() {
		return PageRequest.of(DEFAULT_CURRENT_PAGE, DEFAULT_PAGE_SIZE, Sort.by(Collections.singletonList(new Sort.Order(Sort.Direction.DESC, "id"))));
	}

	public static Pageable createPageRequestWithMultipleSortParams() {
		List<Sort.Order> orderList = new ArrayList<>();
		orderList.add(new Sort.Order(Sort.Direction.DESC, "id"));
		orderList.add(new Sort.Order(Sort.Direction.ASC, "name"));
		return PageRequest.of(DEFAULT_CURRENT_PAGE, DEFAULT_PAGE_SIZE, Sort.by(orderList));
	}

	public static Pageable createPageRequestWithDefaultValues() {
		return PageRequest.of(DEFAULT_CURRENT_PAGE, DEFAULT_PAGE_SIZE);
	}

	// endregion

}
