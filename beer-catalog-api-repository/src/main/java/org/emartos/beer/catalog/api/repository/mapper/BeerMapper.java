package org.emartos.beer.catalog.api.repository.mapper;

import org.emartos.beer.catalog.api.repository.entity.Beer;
import org.emartos.beer.catalog.api.repository.entity.BeerType;
import org.emartos.beer.catalog.api.repository.entity.Manufacturer;
import org.emartos.beer.catalog.api.repository.model.BeerDto;
import org.emartos.beer.catalog.api.repository.model.BeerTypeDto;
import org.emartos.beer.catalog.api.repository.model.ManufacturerDto;
import org.emartos.beer.catalog.api.repository.repository.BeerTypeRepository;
import org.emartos.beer.catalog.api.repository.repository.ManufacturerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BeerMapper {

	private static final Logger LOGGER = LoggerFactory.getLogger(BeerMapper.class);

	private final BeerTypeRepository beerTypeRepository;
	private final ManufacturerRepository manufacturerRepository;

	public BeerMapper(@Qualifier("jpaBeerTypeRepositoryImpl") BeerTypeRepository beerTypeRepository,
					  @Qualifier("jpaManufacturerRepositoryImpl")ManufacturerRepository manufacturerRepository) {
		this.beerTypeRepository = beerTypeRepository;
		this.manufacturerRepository = manufacturerRepository;
	}

	public BeerDto beerToBeerDto(Beer beer) {
		LOGGER.debug(">> beerToBeerDto() beer {}", beer);

		if (beer == null || beer.getId() == null) {
			LOGGER.debug("<< beerToBeerDto() beer or beer id null");
			return null;
		}

		BeerTypeDto beerTypeDto = beerTypeRepository.getById(beer.getBeerType().getId()).orElse(new BeerTypeDto());
		ManufacturerDto manufacturerDto = manufacturerRepository.getById(beer.getManufacturer().getId()).orElse(new ManufacturerDto());

		BeerDto beerDto = BeerDto.builder()
									   .id(beer.getId())
									   .name(beer.getName())
									   .description(beer.getDescription())
								       .graduation(beer.getGraduation())
								       .beerTypeId(beerTypeDto.getId())
								       .manufacturerId(manufacturerDto.getId())
								       .build();

		LOGGER.debug("<< beerToBeerDto() beerDto {}", beerDto);
		return beerDto;
	}

	public Beer beerDtoToBeer(BeerDto beerDto) {
		LOGGER.debug(">> beerDtoToBeer() beerDto {}", beerDto);

		if (beerDto == null) {
			LOGGER.debug("<< beerDtoToBeer() beerDto null");
			return null;
		}

		Beer beer = Beer.builder()
								 .id(beerDto.getId())
								 .name(beerDto.getName())
								 .description(beerDto.getDescription())
								 .graduation(beerDto.getGraduation())
								 .beerType(BeerType.builder().id(beerDto.getBeerTypeId()).build())
								 .manufacturer(Manufacturer.builder().id(beerDto.getManufacturerId()).build())
								 .build();

		LOGGER.debug("<< beerDtoToBeer() beer {}", beer);
		return beer;
	}

	public List<BeerDto> beerListToBeerDtoList(List<Beer> beerList) {
		LOGGER.trace(">> beerListToBeerDtoList() beerList {}", beerList);

		if (beerList == null || beerList.isEmpty()) {
			LOGGER.trace("<< beerListToBeerDtoList() list was null or empty -> beerList {}", beerList);
			return new ArrayList<>();
		}
		List<BeerDto> beerDtoList = new ArrayList<>();
		for (Beer beer : beerList) {
			beerDtoList.add(beerToBeerDto(beer));
		}

		LOGGER.trace("<< beerListToBeerDtoList() beerDtoList {}", beerDtoList);
		return beerDtoList;
	}

}
