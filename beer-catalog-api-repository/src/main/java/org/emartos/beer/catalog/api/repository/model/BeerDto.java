package org.emartos.beer.catalog.api.repository.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BeerDto {

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Long id;
	private String name;
	private String description;
	private Float graduation;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Long beerTypeId;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Long externalId;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String externalBeerType;

	private Long manufacturerId;

}
