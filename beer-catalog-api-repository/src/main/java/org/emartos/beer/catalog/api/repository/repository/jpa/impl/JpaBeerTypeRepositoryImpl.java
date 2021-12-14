package org.emartos.beer.catalog.api.repository.repository.jpa.impl;

import org.emartos.beer.catalog.api.repository.entity.BeerType;
import org.emartos.beer.catalog.api.repository.mapper.BeerTypeMapper;
import org.emartos.beer.catalog.api.repository.model.BeerTypeDto;
import org.emartos.beer.catalog.api.repository.repository.BeerTypeRepository;
import org.emartos.beer.catalog.api.repository.repository.jpa.JpaBeerTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
@Qualifier("jpaBeerTypeRepositoryImpl")
@Transactional
public class JpaBeerTypeRepositoryImpl implements BeerTypeRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(JpaBeerTypeRepositoryImpl.class);

	private final BeerTypeMapper beerTypeMapper;
	@Autowired
	private JpaBeerTypeRepository jpaBeerTypeRepository;

	public JpaBeerTypeRepositoryImpl(BeerTypeMapper beerTypeMapper) {
		this.beerTypeMapper = beerTypeMapper;
	}

	@Override
	public List<BeerTypeDto> getAll() {
		LOGGER.debug(">> getAll()");

		List<BeerType> beerList =  jpaBeerTypeRepository.findAll();
		List<BeerTypeDto> beerDtoList = beerTypeMapper.beerTypeListToBeerTypeDtoList(beerList);

		LOGGER.debug("<< getAll() beerDtoList {}", beerDtoList);
		return beerDtoList;
	}

	@Override
	public Optional<BeerTypeDto> getById(Long id) {
		LOGGER.debug(">> getById() id {}", id);

		BeerType beer = jpaBeerTypeRepository.findById(id).orElse(new BeerType());
		BeerTypeDto beerDto = beerTypeMapper.beerTypeToBeerTypeDto(beer);
		Optional<BeerTypeDto> beerDtoOptional = Optional.ofNullable(beerDto);

		LOGGER.debug("<< getById() beerDtoOptional {}", beerDtoOptional);
		return beerDtoOptional;
	}

	@Override
	public BeerTypeDto create(BeerTypeDto beerDto) {
		LOGGER.debug(">> create() beerDto {}", beerDto);

		BeerType beerToPersist = beerTypeMapper.beerTypeDtoToBeerType(beerDto);
		BeerType beerPersisted =  jpaBeerTypeRepository.save(beerToPersist);
		BeerTypeDto beerDtoPersisted = beerTypeMapper.beerTypeToBeerTypeDto(beerPersisted);

		LOGGER.debug("<< create() beerDtoPersisted {}", beerDtoPersisted);
		return beerDtoPersisted;
	}

	@Override
	public BeerTypeDto update(BeerTypeDto beerDto) {
		LOGGER.debug(">> update() beerDto {}", beerDto);

		BeerType beerToPersist = beerTypeMapper.beerTypeDtoToBeerType(beerDto);
		BeerType beerPersisted =  jpaBeerTypeRepository.save(beerToPersist);
		BeerTypeDto beerDtoPersisted = beerTypeMapper.beerTypeToBeerTypeDto(beerPersisted);

		LOGGER.debug("<< update() beerDtoPersisted {}", beerDtoPersisted);
		return beerDtoPersisted;
	}

	@Override
	public boolean deleteById(Long id) {
		LOGGER.debug(">> deleteById() id {}", id);

		boolean deleted = jpaBeerTypeRepository.deleteBeerTypeById(id) > 0;

		LOGGER.debug("<< deleteById() deleted {}", deleted);
		return deleted;
	}

}
