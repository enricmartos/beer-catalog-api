package org.emartos.beer.catalog.api.repository.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BeerFilterDto {

	private String name;
	private String description;
	private Float minGraduation;
	private Float maxGraduation;
	private Long beerTypeId;
	private Long manufacturerId;

}
