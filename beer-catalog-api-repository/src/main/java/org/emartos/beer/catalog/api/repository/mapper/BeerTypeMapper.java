package org.emartos.beer.catalog.api.repository.mapper;

import org.emartos.beer.catalog.api.repository.entity.BeerType;
import org.emartos.beer.catalog.api.repository.model.BeerTypeDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BeerTypeMapper {

	private static final Logger LOGGER = LoggerFactory.getLogger(BeerTypeMapper.class);

	public BeerTypeDto beerTypeToBeerTypeDto(BeerType beerType) {
		LOGGER.debug(">> beerTypeToBeerTypeDto() beerType {}", beerType);

		if (beerType == null || beerType.getId() == null) {
			LOGGER.debug("<< beerTypeToBeerTypeDto() beerType or beerType id null");
			return null;
		}

		BeerTypeDto beerTypeDto = BeerTypeDto.builder()
									   .id(beerType.getId())
									   .name(beerType.getName())
									   .build();

		LOGGER.debug("<< beerTypeToBeerTypeDto() beerTypeDto {}", beerTypeDto);
		return beerTypeDto;
	}

	public BeerType beerTypeDtoToBeerType(BeerTypeDto beerTypeDto) {
		LOGGER.debug(">> beerTypeDtoToBeerType() beerTypeDto {}", beerTypeDto);

		if (beerTypeDto == null) {
			LOGGER.debug("<< beerTypeDtoToBeerType() beerTypeDto null");
			return null;
		}

		BeerType beerType = BeerType.builder()
							  			.id(beerTypeDto.getId())
									   .name(beerTypeDto.getName())
									   .build();

		LOGGER.debug("<< beerTypeDtoToBeerType() beerType {}", beerType);
		return beerType;
	}

	public List<BeerTypeDto> beerTypeListToBeerTypeDtoList(List<BeerType> beerTypeList) {
		LOGGER.trace(">> beerTypeListToBeerTypeDtoList() beerTypeList {}", beerTypeList);

		if (beerTypeList == null || beerTypeList.isEmpty()) {
			LOGGER.trace("<< beerTypeListToBeerTypeDtoList() list was null or empty -> beerTypeList {}", beerTypeList);
			return new ArrayList<>();
		}
		List<BeerTypeDto> beerTypeDtoList = new ArrayList<>();
		for (BeerType beerType : beerTypeList) {
			beerTypeDtoList.add(beerTypeToBeerTypeDto(beerType));
		}

		LOGGER.trace("<< beerTypeListToBeerTypeDtoList() beerTypeDtoList {}", beerTypeDtoList);
		return beerTypeDtoList;
	}

}
