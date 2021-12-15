package org.emartos.beer.catalog.api.core.helper;

import org.emartos.beer.catalog.api.core.exception.BadRequestException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.emartos.beer.catalog.api.core.helper.ValidationHelper.checkNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
public class ValidationHelperTest {

	@Test
	public void testCheckNotNull() {
		assertThrows(BadRequestException.class, () -> checkNotNull(null, "paramName"));
	}

}
