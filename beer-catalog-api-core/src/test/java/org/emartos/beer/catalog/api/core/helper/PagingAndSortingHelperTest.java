package org.emartos.beer.catalog.api.core.helper;

import org.emartos.beer.catalog.api.core.dto.PageableResponseDto;
import org.emartos.beer.catalog.api.core.exception.BadRequestException;
import org.emartos.beer.catalog.api.repository.model.BeerDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import static org.emartos.beer.catalog.api.core.BeerCatalogApiCoreTestUtils.*;
import static org.emartos.beer.catalog.api.core.helper.PagingAndSortingHelper.buildPageableResponseDto;
import static org.emartos.beer.catalog.api.core.helper.PagingAndSortingHelper.getPageableRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
public class PagingAndSortingHelperTest {

	@Test
	public void testBuildPageableResponseDto() {
		Page<BeerDto> beerTypeDtoPage = getBeerDtoPage(BEER_ID);

		PageableResponseDto<BeerDto> expectedPageableResponseDto = getBeerPageableResponseDto(
				beerTypeDtoPage.getContent(), 0, 1, 1);

		assertEquals(expectedPageableResponseDto, buildPageableResponseDto(beerTypeDtoPage));
	}

	@Test
	public void testGetPageableRequest() throws BadRequestException {
		int currentPage = 0;
		int pageSize = 25;

		// Single validSortParam param ASC
		String[] validSortParam = new String[]{"id","asc"};
		Pageable expectedPageRequest = getPageRequestWithSingleSortParamAsc(currentPage, pageSize);
		assertEquals(expectedPageRequest, getPageableRequest(currentPage, pageSize, validSortParam));

		// Single validSortParam param DESC
		validSortParam = new String[]{"id","desc"};
		expectedPageRequest = getPageRequestWithSingleSortParamDesc(currentPage, pageSize);
		assertEquals(expectedPageRequest, getPageableRequest(currentPage, pageSize, validSortParam));

		// Single validSortParam with only sort direction
		String[] invalidSortParamWithOnlySortDirection = new String[]{"asc"};
		assertThrows(BadRequestException.class, () -> getPageableRequest(currentPage, pageSize, invalidSortParamWithOnlySortDirection));

		// Single validSortParam param with Invalid Sort Direction
		String[] invalidSortParamWithInvalidSortDirection = new String[]{"id,descWithTypo"};
		assertThrows(BadRequestException.class, () -> getPageableRequest(currentPage, pageSize, invalidSortParamWithInvalidSortDirection));

		// Multiple validSortParam params
		validSortParam = new String[]{"id,desc","name,asc"};
		expectedPageRequest = getPageRequestWithMultipleSortParams(currentPage, pageSize);
		assertEquals(expectedPageRequest, getPageableRequest(currentPage, pageSize, validSortParam));
	}

}
