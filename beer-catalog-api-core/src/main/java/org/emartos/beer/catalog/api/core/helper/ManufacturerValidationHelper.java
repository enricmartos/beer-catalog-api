package org.emartos.beer.catalog.api.core.helper;

import org.emartos.beer.catalog.api.core.exception.BadRequestException;
import org.emartos.beer.catalog.api.core.util.ValidationUtil;
import org.emartos.beer.catalog.api.repository.model.ManufacturerDto;

public class ManufacturerValidationHelper {

	private ManufacturerValidationHelper() {
		// Default private constructor
	}

	public static void validateManufacturer(ManufacturerDto manufacturerDto) throws BadRequestException {
		validateName(manufacturerDto.getName());
		validateNationality(manufacturerDto.getNationality());
	}

	// region Private methods

	private static void validateName(String name) throws BadRequestException {
		ValidationUtil.checkNotNull(name, "name");
	}

	private static void validateNationality(String nationality) throws BadRequestException {
		ValidationUtil.checkNotNull(nationality, "nationality");
	}

	// endregion

}
