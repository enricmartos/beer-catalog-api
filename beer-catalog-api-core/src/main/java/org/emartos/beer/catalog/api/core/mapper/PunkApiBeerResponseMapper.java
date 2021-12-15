package org.emartos.beer.catalog.api.core.mapper;

import org.emartos.beer.catalog.api.core.dto.punkapi.PunkApiBeerResponseDto;
import org.emartos.beer.catalog.api.repository.model.BeerDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.emartos.beer.catalog.api.core.util.ConstantsUtil.PUNK_API_EXTERNAL_MANUFACTURER_ID;

@Component
public class PunkApiBeerResponseMapper {

	private static final Logger LOGGER = LoggerFactory.getLogger(PunkApiBeerResponseMapper.class);

	public BeerDto punkApiBeerResponseDtoToBeerDto(PunkApiBeerResponseDto punkApiBeerResponseDto) {
		LOGGER.debug(">> punkApiBeerResponseDtoToBeerDto() punkApiBeerResponseDto {}", punkApiBeerResponseDto);

		if (punkApiBeerResponseDto == null || punkApiBeerResponseDto.getId() == null) {
			LOGGER.debug("<< punkApiBeerResponseDtoToBeerDto() punkApiBeerResponseDto or punkApiBeerResponseDto id null");
			return null;
		}

		BeerDto beerDto = BeerDto.builder()
								 .name(punkApiBeerResponseDto.getName())
								 .description(punkApiBeerResponseDto.getDescription())
								 .graduation(Float.valueOf(punkApiBeerResponseDto.getGraduation()))
								 .externalId(Long.valueOf(punkApiBeerResponseDto.getId()))
								 .externalBeerType(punkApiBeerResponseDto.getBeerType())
								 .manufacturerId(PUNK_API_EXTERNAL_MANUFACTURER_ID)
								 .build();

		LOGGER.debug("<< punkApiBeerResponseDtoToBeerDto() beerDto {}", beerDto);
		return beerDto;
	}

	public List<BeerDto> punkApiBeerResponseListDtoToBeerListDto(List<PunkApiBeerResponseDto> punkApiBeerResponseDtoList) {
		LOGGER.trace(">> punkApiBeerResponseListDtoToBeerListDto() punkApiBeerResponseDtoList {}", punkApiBeerResponseDtoList);

		if (punkApiBeerResponseDtoList == null || punkApiBeerResponseDtoList.isEmpty()) {
			LOGGER.trace("<< punkApiBeerResponseListDtoToBeerListDto() list was null or empty -> punkApiBeerResponseDtoList {}", punkApiBeerResponseDtoList);
			return new ArrayList<>();
		}
		List<BeerDto> beerDtoList = new ArrayList<>();
		for (PunkApiBeerResponseDto punkApiBeerResponseDto : punkApiBeerResponseDtoList) {
			beerDtoList.add(punkApiBeerResponseDtoToBeerDto(punkApiBeerResponseDto));
		}

		LOGGER.trace("<< punkApiBeerResponseListDtoToBeerListDto() beerDtoList {}", beerDtoList);
		return beerDtoList;
	}



}
