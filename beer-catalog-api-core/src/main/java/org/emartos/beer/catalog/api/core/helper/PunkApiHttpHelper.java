package org.emartos.beer.catalog.api.core.helper;

import org.emartos.beer.catalog.api.repository.model.BeerFilterDto;
import org.springframework.data.domain.Pageable;

import static org.emartos.beer.catalog.api.core.helper.HttpHelper.getUrl;

public class PunkApiHttpHelper {

	private PunkApiHttpHelper() {
		// Private constructor
	}

	public static String getUrlRequestParams(BeerFilterDto beerFilterDto, Pageable pageable) {
		String urlWithRequestParams = getUrl("?page=", String.valueOf(pageable.getPageNumber() + 1),
				"&per_page=", String.valueOf(pageable.getPageSize()));
		if (beerFilterDto.getName() != null) {
			urlWithRequestParams = getUrl(urlWithRequestParams, "&beer_name=", beerFilterDto.getName());
		}

		if (beerFilterDto.getMinGraduation() != null && beerFilterDto.getMaxGraduation() != null) {
			urlWithRequestParams = getUrl(urlWithRequestParams, "&abv_gt=", String.valueOf(beerFilterDto.getMinGraduation()), "&abv_lt=", String.valueOf(beerFilterDto.getMaxGraduation()));
		} else if (beerFilterDto.getMinGraduation() != null) {
			urlWithRequestParams = getUrl(urlWithRequestParams, "&abv_gt=", String.valueOf(beerFilterDto.getMinGraduation()));
		} else if (beerFilterDto.getMaxGraduation() != null) {
			urlWithRequestParams = getUrl(urlWithRequestParams, "&abv_gt=", String.valueOf(beerFilterDto.getMaxGraduation()));
		}
		return urlWithRequestParams;
	}

}
