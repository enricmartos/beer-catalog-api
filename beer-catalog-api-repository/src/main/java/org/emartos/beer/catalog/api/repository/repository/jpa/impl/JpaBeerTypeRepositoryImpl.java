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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
	public Page<BeerTypeDto> getAll(Pageable pageable) {
		LOGGER.debug(">> getAll()");

		Page<BeerType> beerTypePage =  jpaBeerTypeRepository.findAll(pageable);
		List<BeerTypeDto> beerTypeDtoList = beerTypeMapper.beerTypeListToBeerTypeDtoList(beerTypePage.getContent());
		Page<BeerTypeDto> beerTypeDtoPage = new PageImpl<>(beerTypeDtoList, pageable, beerTypePage.getTotalElements());

		LOGGER.debug("<< getAll() beerTypeDtoPage {}", beerTypeDtoPage);
		return beerTypeDtoPage;
	}

	@Override
	public Optional<BeerTypeDto> getById(Long id) {
		LOGGER.debug(">> getById() id {}", id);

		BeerType beer = jpaBeerTypeRepository.findById(id).orElse(new BeerType());
		BeerTypeDto beerTypeDto = beerTypeMapper.beerTypeToBeerTypeDto(beer);
		Optional<BeerTypeDto> beerTypeDtoOptional = Optional.ofNullable(beerTypeDto);

		LOGGER.debug("<< getById() beerTypeDtoOptional {}", beerTypeDtoOptional);
		return beerTypeDtoOptional;
	}

	@Override
	public BeerTypeDto create(BeerTypeDto beerTypeDto) {
		LOGGER.debug(">> create() beerTypeDto {}", beerTypeDto);

		BeerTypeDto beerTypeDtoPersisted = createOrUpdateBeerType(beerTypeDto);

		LOGGER.debug("<< create() beerTypeDtoPersisted {}", beerTypeDtoPersisted);
		return beerTypeDtoPersisted;
	}

	@Override
	public BeerTypeDto update(BeerTypeDto beerTypeDto) {
		LOGGER.debug(">> update() beerTypeDto {}", beerTypeDto);

		BeerTypeDto beerTypeDtoPersisted = createOrUpdateBeerType(beerTypeDto);

		LOGGER.debug("<< update() beerTypeDtoPersisted {}", beerTypeDtoPersisted);
		return beerTypeDtoPersisted;
	}

	@Override
	public boolean deleteById(Long id) {
		LOGGER.debug(">> deleteById() id {}", id);

		boolean deleted = jpaBeerTypeRepository.deleteBeerTypeById(id) > 0;

		LOGGER.debug("<< deleteById() deleted {}", deleted);
		return deleted;
	}
	
	// region Private methods

	private BeerTypeDto createOrUpdateBeerType(BeerTypeDto beerTypeDto) {
		LOGGER.trace(">> createOrUpdateBeerType() beerTypeDto {}", beerTypeDto);

		BeerType beerToPersist = beerTypeMapper.beerTypeDtoToBeerType(beerTypeDto);
		BeerType beerPersisted =  jpaBeerTypeRepository.save(beerToPersist);
		BeerTypeDto beerTypeDtoPersisted = beerTypeMapper.beerTypeToBeerTypeDto(beerPersisted);

		LOGGER.trace("<< createOrUpdateBeerType() beerTypeDtoPersisted {}", beerTypeDtoPersisted);
		return beerTypeDtoPersisted;
	}
	
	// endregion

}
