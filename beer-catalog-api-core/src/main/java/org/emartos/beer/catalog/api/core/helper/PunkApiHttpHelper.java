package org.emartos.beer.catalog.api.core.helper;

import org.emartos.beer.catalog.api.repository.model.BeerFilterDto;
import org.springframework.data.domain.Pageable;

import static org.emartos.beer.catalog.api.core.helper.HttpHelper.getUrl;

public class PunkApiHttpHelper {

	private static final String CURRENT_PAGE_PARAM = "?page=";
	private static final String PAGE_SIZE_PARAM = "&per_page=";
	private static final String BEER_NAME_PARAM = "&beer_name=";
	private static final String BEER_GRADUATION_MIN_PARAM = "&abv_gt=";
	private static final String BEER_GRADUATION_MAX_PARAM = "&abv_lt=";

	private PunkApiHttpHelper() {
		// Private constructor
	}

	public static String getUrlRequestParams(BeerFilterDto beerFilterDto, Pageable pageable) {
		String urlWithRequestParams = getUrl(CURRENT_PAGE_PARAM, String.valueOf(pageable.getPageNumber() + 1), PAGE_SIZE_PARAM, String.valueOf(pageable.getPageSize()));
		if (beerFilterDto.getName() != null) {
			String punkApiName = beerFilterDto.getName().replace(" ", "_");
			urlWithRequestParams = getUrl(urlWithRequestParams, BEER_NAME_PARAM, punkApiName);
		}

		if (beerFilterDto.getMinGraduation() != null && beerFilterDto.getMaxGraduation() != null) {
			urlWithRequestParams = getUrl(urlWithRequestParams, BEER_GRADUATION_MIN_PARAM, String.valueOf(beerFilterDto.getMinGraduation()),
					BEER_GRADUATION_MAX_PARAM, String.valueOf(beerFilterDto.getMaxGraduation()));
		} else if (beerFilterDto.getMinGraduation() != null) {
			urlWithRequestParams = getUrl(urlWithRequestParams, BEER_GRADUATION_MIN_PARAM, String.valueOf(beerFilterDto.getMinGraduation()));
		} else if (beerFilterDto.getMaxGraduation() != null) {
			urlWithRequestParams = getUrl(urlWithRequestParams, BEER_GRADUATION_MAX_PARAM, String.valueOf(beerFilterDto.getMaxGraduation()));
		}
		return urlWithRequestParams;
	}

}
