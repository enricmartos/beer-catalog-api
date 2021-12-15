package org.emartos.beer.catalog.api.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.emartos.beer.catalog.api.core.helper.ValidationHelper.NOT_FOUND_ERROR_MESSAGE;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = NOT_FOUND_ERROR_MESSAGE)
public class NotFoundException extends BeerCatalogApiException {

	public NotFoundException() {
		super(NOT_FOUND_ERROR_MESSAGE);
	}

}
