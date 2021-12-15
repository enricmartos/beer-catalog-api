package org.emartos.beer.catalog.api.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.emartos.beer.catalog.api.core.helper.ValidationHelper.BAD_REQUEST_ERROR_MESSAGE;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = BAD_REQUEST_ERROR_MESSAGE)
public class BadRequestException extends BeerCatalogApiException {

	public BadRequestException(String message) {
		super(message);
	}

}
