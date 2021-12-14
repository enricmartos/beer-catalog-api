package org.emartos.beer.catalog.api.repository.mapper;

import org.emartos.beer.catalog.api.repository.entity.Manufacturer;
import org.emartos.beer.catalog.api.repository.model.ManufacturerDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ManufacturerMapper {

	private static final Logger LOGGER = LoggerFactory.getLogger(ManufacturerMapper.class);

	public ManufacturerDto manufacturerToManufacturerDto(Manufacturer manufacturer) {
		LOGGER.debug(">> manufacturerToManufacturerDto() manufacturer {}", manufacturer);

		if (manufacturer == null || manufacturer.getId() == null) {
			LOGGER.debug("<< manufacturerToManufacturerDto() manufacturer or manufacturer id null");
			return null;
		}

		ManufacturerDto manufacturerDto = ManufacturerDto.builder()
									   .id(manufacturer.getId())
									   .name(manufacturer.getName())
									   .nationality(manufacturer.getNationality())
									   .build();

		LOGGER.debug("<< manufacturerToManufacturerDto() manufacturerDto {}", manufacturerDto);
		return manufacturerDto;
	}

	public Manufacturer manufacturerDtoToManufacturer(ManufacturerDto manufacturerDto) {
		LOGGER.debug(">> manufacturerDtoToManufacturer() manufacturerDto {}", manufacturerDto);

		if (manufacturerDto == null) {
			LOGGER.debug("<< manufacturerDtoToManufacturer() manufacturerDto null");
			return null;
		}

		Manufacturer manufacturer = Manufacturer.builder()
							  			.id(manufacturerDto.getId())
									   .name(manufacturerDto.getName())
									   .nationality(manufacturerDto.getNationality())
									   .build();

		LOGGER.debug("<< manufacturerDtoToManufacturer() manufacturer {}", manufacturer);
		return manufacturer;
	}

	public List<ManufacturerDto> manufacturerListToManufacturerDtoList(List<Manufacturer> manufacturerList) {
		LOGGER.trace(">> manufacturerListToManufacturerDtoList() manufacturerList {}", manufacturerList);

		if (manufacturerList == null || manufacturerList.isEmpty()) {
			LOGGER.trace("<< manufacturerListToManufacturerDtoList() list was null or empty -> manufacturerList {}", manufacturerList);
			return new ArrayList<>();
		}
		List<ManufacturerDto> manufacturerDtoList = new ArrayList<>();
		for (Manufacturer manufacturer : manufacturerList) {
			manufacturerDtoList.add(manufacturerToManufacturerDto(manufacturer));
		}

		LOGGER.trace("<< manufacturerListToManufacturerDtoList() manufacturerDtoList {}", manufacturerDtoList);
		return manufacturerDtoList;
	}

}
