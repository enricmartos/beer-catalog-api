package org.emartos.beer.catalog.api.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Beer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String name;

	@NotNull
	private String description;

	@NotNull
	private Float graduation;

	@ManyToOne(optional = false)
	@JoinColumn(name = "beerTypeId", referencedColumnName = "id", nullable = false, updatable = false)
	private BeerType beerType;

	@ManyToOne(optional = false)
	@JoinColumn(name = "manufacturerId", referencedColumnName = "id", nullable = false, updatable = false)
	private Manufacturer manufacturer;

}
