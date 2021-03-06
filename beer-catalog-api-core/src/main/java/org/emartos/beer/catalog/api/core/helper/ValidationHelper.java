package org.emartos.beer.catalog.api.core.helper;

import org.emartos.beer.catalog.api.core.exception.BadRequestException;

public class ValidationHelper {

	public static final String NOT_FOUND_ERROR_MESSAGE = "This resource does not exist.";
	public static final String BAD_REQUEST_ERROR_MESSAGE = "The parameters of the request are not valid.";

	private ValidationHelper() {
		// Default private constructor
	}

	public static void checkNotNull(Object value, String parameterName) throws BadRequestException {
		if (value == null) {
			throw new BadRequestException(String.format("Field %s can not be null", parameterName));
		}
	}


}
