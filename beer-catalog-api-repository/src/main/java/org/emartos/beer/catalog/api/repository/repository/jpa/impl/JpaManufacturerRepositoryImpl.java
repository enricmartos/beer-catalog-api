package org.emartos.beer.catalog.api.repository.repository.jpa.impl;

import org.emartos.beer.catalog.api.repository.entity.Manufacturer;
import org.emartos.beer.catalog.api.repository.mapper.ManufacturerMapper;
import org.emartos.beer.catalog.api.repository.model.ManufacturerDto;
import org.emartos.beer.catalog.api.repository.repository.ManufacturerRepository;
import org.emartos.beer.catalog.api.repository.repository.jpa.JpaManufacturerRepository;
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
@Qualifier("jpaManufacturerRepositoryImpl")
@Transactional
public class JpaManufacturerRepositoryImpl implements ManufacturerRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(JpaManufacturerRepositoryImpl.class);

	private final ManufacturerMapper manufacturerMapper;
	@Autowired
	private JpaManufacturerRepository jpaManufacturerRepository;

	public JpaManufacturerRepositoryImpl(ManufacturerMapper manufacturerMapper) {
		this.manufacturerMapper = manufacturerMapper;
	}

	@Override
	public Page<ManufacturerDto> getAll(Pageable pageable) {
		LOGGER.debug(">> getAll()");

		Page<Manufacturer> manufacturerPage = jpaManufacturerRepository.findAll(pageable);
		List<ManufacturerDto> manufacturerDtoList = manufacturerMapper.manufacturerListToManufacturerDtoList(manufacturerPage.getContent());
		Page<ManufacturerDto> manufacturerDtoPage = new PageImpl<>(manufacturerDtoList, pageable, manufacturerPage.getTotalElements());

		LOGGER.debug("<< getAll() manufacturerDtoList {}", manufacturerDtoList);
		return manufacturerDtoPage;
	}

	@Override
	public Optional<ManufacturerDto> getById(Long id) {
		LOGGER.debug(">> getById() id {}", id);

		Manufacturer manufacturer = jpaManufacturerRepository.findById(id).orElse(new Manufacturer());
		ManufacturerDto manufacturerDto = manufacturerMapper.manufacturerToManufacturerDto(manufacturer);
		Optional<ManufacturerDto> manufacturerDtoOptional = Optional.ofNullable(manufacturerDto);

		LOGGER.debug("<< getById() manufacturerDtoOptional {}", manufacturerDtoOptional);
		return manufacturerDtoOptional;
	}

	@Override
	public ManufacturerDto create(ManufacturerDto manufacturerDto) {
		LOGGER.debug(">> create() manufacturerDto {}", manufacturerDto);

		ManufacturerDto manufacturerDtoPersisted = createOrUpdateManufacturer(manufacturerDto);

		LOGGER.debug("<< create() manufacturerDtoPersisted {}", manufacturerDtoPersisted);
		return manufacturerDtoPersisted;
	}

	@Override
	public ManufacturerDto update(ManufacturerDto manufacturerDto) {
		LOGGER.debug(">> update() manufacturerDto {}", manufacturerDto);

		ManufacturerDto manufacturerDtoPersisted = createOrUpdateManufacturer(manufacturerDto);

		LOGGER.debug("<< update() manufacturerDtoPersisted {}", manufacturerDtoPersisted);
		return manufacturerDtoPersisted;
	}

	@Override
	public boolean deleteById(Long id) {
		LOGGER.debug(">> deleteById() id {}", id);

		boolean deleted = jpaManufacturerRepository.deleteManufacturerById(id) > 0;

		LOGGER.debug("<< deleteById() deleted {}", deleted);
		return deleted;
	}

	// region Private methods

	private ManufacturerDto createOrUpdateManufacturer(ManufacturerDto manufacturerDto) {
		LOGGER.trace(">> createOrUpdateManufacturer() manufacturerDto {}", manufacturerDto);

		Manufacturer manufacturerToPersist = manufacturerMapper.manufacturerDtoToManufacturer(manufacturerDto);
		Manufacturer manufacturerPersisted =  jpaManufacturerRepository.save(manufacturerToPersist);
		ManufacturerDto manufacturerDtoPersisted = manufacturerMapper.manufacturerToManufacturerDto(manufacturerPersisted);

		LOGGER.trace("<< createOrUpdateManufacturer() manufacturerDtoPersisted {}", manufacturerDtoPersisted);
		return manufacturerDtoPersisted;
	}

	// endregion

}
