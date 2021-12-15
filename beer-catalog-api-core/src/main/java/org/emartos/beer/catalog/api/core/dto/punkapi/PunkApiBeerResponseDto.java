package org.emartos.beer.catalog.api.core.dto.punkapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PunkApiBeerResponseDto {

	private String id;
	private String name;
	private String description;
	@JsonProperty("abv")
	@SerializedName("abv")
	private String graduation;
	@JsonProperty("tagline")
	@SerializedName("tagline")
	private String beerType;

}
