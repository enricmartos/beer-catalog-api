package org.emartos.beer.catalog.api.repository.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BeerDto {

	private Long id;
	private String name;
	private String description;
	private Float graduation;
	private Long beerTypeId;
	private Long manufacturerId;

}
