package org.emartos.beer.catalog.api.core.helper;

import org.emartos.beer.catalog.api.core.exception.BadRequestException;
import org.emartos.beer.catalog.api.core.util.ValidationUtil;
import org.emartos.beer.catalog.api.repository.model.BeerDto;

public class BeerValidationHelper {

	private BeerValidationHelper() {
		// Default private constructor
	}

	public static void validateBeer(BeerDto beerDto) throws BadRequestException {
		validateName(beerDto.getName());
		validateDescription(beerDto.getDescription());
		validateGraduation(beerDto.getGraduation());
	}

	// region Private methods

	private static void validateName(String name) throws BadRequestException {
		ValidationUtil.checkNotNull(name, "name");
	}

	private static void validateDescription(String description) throws BadRequestException {
		ValidationUtil.checkNotNull(description, "description");
	}

	private static void validateGraduation(Float graduation) throws BadRequestException {
		ValidationUtil.checkNotNull(graduation, "graduation");
	}

	// endregion

}
