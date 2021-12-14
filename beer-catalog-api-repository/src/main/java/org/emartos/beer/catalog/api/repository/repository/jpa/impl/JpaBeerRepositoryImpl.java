package org.emartos.beer.catalog.api.repository.repository.jpa.impl;

import org.emartos.beer.catalog.api.repository.entity.Beer;
import org.emartos.beer.catalog.api.repository.mapper.BeerMapper;
import org.emartos.beer.catalog.api.repository.model.BeerDto;
import org.emartos.beer.catalog.api.repository.repository.BeerRepository;
import org.emartos.beer.catalog.api.repository.repository.jpa.JpaBeerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
@Qualifier("jpaBeerRepositoryImpl")
@Transactional
public class JpaBeerRepositoryImpl implements BeerRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(JpaBeerRepositoryImpl.class);

	private final BeerMapper beerMapper;
	@Autowired
	private JpaBeerRepository jpaBeerRepository;

	public JpaBeerRepositoryImpl(BeerMapper beerMapper) {
		this.beerMapper = beerMapper;
	}

	@Override
	public List<BeerDto> getAll() {
		LOGGER.debug(">> getAll()");

		List<Beer> beerList =  jpaBeerRepository.findAll();
		List<BeerDto> beerDtoList = beerMapper.beerListToBeerDtoList(beerList);

		LOGGER.debug("<< getAll() beerDtoList {}", beerDtoList);
		return beerDtoList;
	}

	@Override
	public Optional<BeerDto> getById(Long id) {
		LOGGER.debug(">> getById() id {}", id);

		Beer beer = jpaBeerRepository.findById(id).orElse(new Beer());
		BeerDto beerDto = beerMapper.beerToBeerDto(beer);
		Optional<BeerDto> beerDtoOptional = Optional.ofNullable(beerDto);

		LOGGER.debug("<< getById() beerDtoOptional {}", beerDtoOptional);
		return beerDtoOptional;
	}

	@Override
	public BeerDto create(BeerDto beerDto) {
		LOGGER.debug(">> create() beerDto {}", beerDto);

		Beer beerToPersist = beerMapper.beerDtoToBeer(beerDto);
		Beer beerPersisted =  jpaBeerRepository.save(beerToPersist);
		BeerDto beerDtoPersisted = beerMapper.beerToBeerDto(beerPersisted);

		LOGGER.debug("<< create() beerDtoPersisted {}", beerDtoPersisted);
		return beerDtoPersisted;
	}

	@Override
	public BeerDto update(BeerDto beerDto) {
		LOGGER.debug(">> update() beerDto {}", beerDto);

		Beer beerToPersist = beerMapper.beerDtoToBeer(beerDto);
		Beer beerPersisted =  jpaBeerRepository.save(beerToPersist);
		BeerDto beerDtoPersisted = beerMapper.beerToBeerDto(beerPersisted);

		LOGGER.debug("<< update() beerDtoPersisted {}", beerDtoPersisted);
		return beerDtoPersisted;
	}

	@Override
	public boolean deleteById(Long id) {
		LOGGER.debug(">> deleteById() id {}", id);

		boolean deleted = jpaBeerRepository.deleteBeerById(id) > 0;

		LOGGER.debug("<< deleteById() deleted {}", deleted);
		return deleted;
	}

}
