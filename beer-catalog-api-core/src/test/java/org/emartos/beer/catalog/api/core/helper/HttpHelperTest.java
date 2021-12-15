package org.emartos.beer.catalog.api.core.helper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.emartos.beer.catalog.api.core.dto.punkapi.PunkApiBeerResponseDto;
import org.emartos.beer.catalog.api.core.exception.BeerCatalogApiException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

import static org.emartos.beer.catalog.api.core.BeerCatalogApiCoreTestUtils.getPunkApiBeerResponseDto;
import static org.emartos.beer.catalog.api.core.helper.HttpHelper.getUrl;
import static org.emartos.beer.catalog.api.core.helper.HttpHelper.parseResponseEntityToObject;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
public class HttpHelperTest {

	@Test
	public void testGetUrl() {
		String expectedUrl = "expected/url/test";
		assertEquals(expectedUrl, getUrl("expected", "/url", "/test"));
	}

	@Test
	public void testParseResponseEntityToObjectOk() throws BeerCatalogApiException {
		List<PunkApiBeerResponseDto> punkApiBeerResponseDtoList = Collections.singletonList(getPunkApiBeerResponseDto());
		String body = new Gson().toJson(punkApiBeerResponseDtoList);
		assertEquals(punkApiBeerResponseDtoList, parseResponseEntityToObject(new ResponseEntity<>(body, HttpStatus.OK), new TypeToken<List<PunkApiBeerResponseDto>>() {
		}.getType(), true));
	}

	@Test
	public void testParseResponseEntityToObjectOkBodyNull() throws BeerCatalogApiException {
		assertNull(parseResponseEntityToObject(new ResponseEntity<>(HttpStatus.OK), null, false));
	}

	@Test(expected = BeerCatalogApiException.class)
	public void testParseResponseEntityToObjectKoBodyNull() throws BeerCatalogApiException {
		parseResponseEntityToObject(new ResponseEntity<>(HttpStatus.OK), null, true);
	}

	@Test(expected = BeerCatalogApiException.class)
	public void testParseResponseEntityToObjectKoIsStatusOkFalse() throws BeerCatalogApiException {
		parseResponseEntityToObject(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR), null, false);
	}

}
