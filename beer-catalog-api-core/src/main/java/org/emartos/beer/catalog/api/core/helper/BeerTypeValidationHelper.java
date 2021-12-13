package org.emartos.beer.catalog.api.core.helper;

import org.emartos.beer.catalog.api.core.exception.BadRequestException;
import org.emartos.beer.catalog.api.core.util.ValidationUtil;
import org.emartos.beer.catalog.api.repository.model.BeerTypeDto;

public class BeerTypeValidationHelper {

	private BeerTypeValidationHelper() {
		// Default private constructor
	}

	public static void validateBeerType(BeerTypeDto beerTypeDto) throws BadRequestException {
		validateName(beerTypeDto.getName());
	}

	// region Private methods

	private static void validateName(String name) throws BadRequestException {
		ValidationUtil.checkNotNull(name, "name");
	}

	// endregion

}
