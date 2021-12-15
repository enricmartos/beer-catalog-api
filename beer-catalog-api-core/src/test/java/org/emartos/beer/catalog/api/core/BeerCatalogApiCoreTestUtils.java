package org.emartos.beer.catalog.api.core;

import org.emartos.beer.catalog.api.core.dto.PageableResponseDto;
import org.emartos.beer.catalog.api.core.dto.punkapi.PunkApiBeerResponseDto;
import org.emartos.beer.catalog.api.repository.entity.Manufacturer;
import org.emartos.beer.catalog.api.repository.model.BeerDto;
import org.emartos.beer.catalog.api.repository.model.BeerTypeDto;
import org.emartos.beer.catalog.api.repository.model.ManufacturerDto;
import org.hibernate.persister.walking.internal.StandardAnyTypeDefinition;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BeerCatalogApiCoreTestUtils {

	public static final String BEER_NAME = "Beer name";
	public static final String NON_EXISTENT_BEER_NAME = "Non Existent Beer name";
	public static final Long BEER_ID = 1L;
	public static final Long NON_EXISTENT_BEER_ID = 2L;
	public static final Long BEER_TYPE_ID = 3L;
	public static final Long NON_EXISTENT_BEER_TYPE_ID = 4L;
	public static final Long MANUFACTURER_ID = 5L;
	public static final Long NON_EXISTENT_MANUFACTURER_ID = 6L;

	private BeerCatalogApiCoreTestUtils() {
		// Private default constructor
	}

	public static PunkApiBeerResponseDto getPunkApiBeerResponseDto() {
		return PunkApiBeerResponseDto.builder()
									 .id("3")
									 .name("Berliner Weisse With Yuzu - B-Sides")
									 .description("Japanese citrus fruit intensifies the sour nature of this German classic.")
									 .graduation("4.2")
									 .beerType("Japanese Citrus Berliner Weisse.")
									 .build();
	}

	public static BeerTypeDto getBeerTypeDto(Long id) {
		return BeerTypeDto.builder()
						  .id(id)
						  .name("Beer Type name")
						  .build();
	}

	public static ManufacturerDto getManufacturerDto(Long id) {
		return ManufacturerDto.builder()
							  .id(id)
							  .name("Beer Type name")
							  .nationality("Nationality")
							  .build();
	}

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

	public static Page<BeerDto> getBeerDtoPage(Long id) {
		List<BeerDto> beerDtoList = Collections.singletonList(getBeerDto(id, BEER_TYPE_ID, MANUFACTURER_ID));
		Pageable pageable = PageRequest.of(0, 25);
		return new PageImpl<>(beerDtoList, pageable, 1);
	}

	public static PageableResponseDto<BeerDto> getBeerPageableResponseDto(List<BeerDto> beerDtoList,
																		  int currentPage,
																		  int pageSize,
																		  int totalPages) {
		return new PageableResponseDto<>(beerDtoList, currentPage, pageSize, totalPages);
	}

	public static Pageable getPageRequestWithSingleSortParamAsc(int currentPage, int pageSize) {
		return PageRequest.of(currentPage, pageSize, Sort.by(Collections.singletonList(new Sort.Order(Sort.Direction.ASC, "id"))));
	}

	public static Pageable getPageRequestWithSingleSortParamDesc(int currentPage, int pageSize) {
		return PageRequest.of(currentPage, pageSize, Sort.by(Collections.singletonList(new Sort.Order(Sort.Direction.DESC, "id"))));
	}

	public static Pageable getPageRequestWithMultipleSortParams(int currentPage, int pageSize) {
		List<Sort.Order> orderList = new ArrayList<>();
		orderList.add(new Sort.Order(Sort.Direction.DESC, "id"));
		orderList.add(new Sort.Order(Sort.Direction.ASC, "name"));
		return PageRequest.of(currentPage, pageSize, Sort.by(orderList));
	}

	public static Pageable getPageRequestWithDefaultValues() {
		return PageRequest.of(0, 25);
	}
}
