package org.emartos.beer.catalog.api.core.helper;

import com.google.gson.Gson;
import org.emartos.beer.catalog.api.core.exception.BeerCatalogApiException;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Type;

public class HttpHelper {

	private HttpHelper() {
		// Private constructor
	}

	public static String getUrl(String... params) {
		StringBuilder urlBuilder = new StringBuilder();
		for (String param : params) {
			urlBuilder.append(param);
		}
		return urlBuilder.toString();
	}

	public static Object parseResponseEntityToObject(ResponseEntity<String> responseEntity, Type type, boolean throwIfNullBody) throws BeerCatalogApiException {
		Object responseBody;
		if (isStatusOk(responseEntity)) {
			if (throwIfNullBody && responseEntity.getBody() == null) {
				throw new BeerCatalogApiException("Punk Api REST API request body is null and not null expected.");
			} else if (responseEntity.getBody() == null) {
				responseBody = null;
			} else {
				responseBody = new Gson().fromJson(responseEntity.getBody(), type);
			}
		} else {
			throw new BeerCatalogApiException("Punk Api REST API request responses an error");
		}

		return responseBody;
	}

	private static boolean isStatusOk(ResponseEntity<String> responseEntity) {
		return responseEntity.getStatusCodeValue() == 200;
	}

}
