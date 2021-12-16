package org.emartos.beer.catalog.api.core.helper;

import org.emartos.beer.catalog.api.repository.model.BeerFilterDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import static org.emartos.beer.catalog.api.core.BeerCatalogApiCoreDataFactory.BEER_TYPE_ID;
import static org.emartos.beer.catalog.api.core.BeerCatalogApiCoreDataFactory.MANUFACTURER_ID;
import static org.emartos.beer.catalog.api.core.BeerCatalogApiCoreDataFactory.createFilteredBeerDto;
import static org.emartos.beer.catalog.api.core.BeerCatalogApiCoreDataFactory.createPageRequestWithDefaultValues;
import static org.emartos.beer.catalog.api.core.helper.PunkApiHttpHelper.getUrlRequestParams;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
public class PunkApiHttpHelperTest {

	@Test
	public void testGetUrlRequestParams() {
		BeerFilterDto beerFilterDto = createFilteredBeerDto(BEER_TYPE_ID, MANUFACTURER_ID);
		Pageable pageable = createPageRequestWithDefaultValues();

		// min graduation and max graduation not null
		String expectedUrl = "?page=1&per_page=25&beer_name=Beer_name&abv_gt=4.0&abv_lt=5.0";
		assertEquals(expectedUrl, getUrlRequestParams(beerFilterDto, pageable));

		// min graduation not null and max graduation null
		beerFilterDto.setMaxGraduation(null);
		expectedUrl = "?page=1&per_page=25&beer_name=Beer_name&abv_gt=4.0";
		assertEquals(expectedUrl, getUrlRequestParams(beerFilterDto, pageable));

		// min graduation null and max graduation not null
		beerFilterDto.setMaxGraduation(5F);
		beerFilterDto.setMinGraduation(null);
		expectedUrl = "?page=1&per_page=25&beer_name=Beer_name&abv_lt=5.0";
		assertEquals(expectedUrl, getUrlRequestParams(beerFilterDto, pageable));
	}

}
