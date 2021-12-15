package org.emartos.beer.catalog.api.repository.repository.jpa.impl;

import org.emartos.beer.catalog.api.repository.entity.Beer;
import org.emartos.beer.catalog.api.repository.mapper.BeerMapper;
import org.emartos.beer.catalog.api.repository.model.BeerDto;
import org.emartos.beer.catalog.api.repository.model.BeerFilterDto;
import org.emartos.beer.catalog.api.repository.repository.BeerRepository;
import org.emartos.beer.catalog.api.repository.repository.jpa.JpaBeerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Qualifier("jpaBeerRepositoryImpl")
@Transactional
public class JpaBeerRepositoryImpl implements BeerRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(JpaBeerRepositoryImpl.class);

	private static final String ATTR_NAME = "name";
	private static final String ATTR_DESCRIPTION = "description";
	private static final String ATTR_GRADUATION = "graduation";
	private static final String ATTR_BEER_TYPE = "beerType";
	private static final String ATTR_MANUFACTURER = "manufacturer";

	private final BeerMapper beerMapper;
	@Autowired
	private JpaBeerRepository jpaBeerRepository;

	private final EntityManager em;

	public JpaBeerRepositoryImpl(BeerMapper beerMapper, EntityManager em) {
		this.beerMapper = beerMapper;
		this.em = em;
	}

	@Override
	public Page<BeerDto> getAllByParams(BeerFilterDto beerFilterDto, Pageable pageable) {
		LOGGER.debug(">> findAllByParams() beerFilterDto {}", beerFilterDto);

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Beer> query = builder.createQuery(Beer.class);
		Root<Beer> root = query.from(Beer.class);
		List<Predicate> predicateList = new ArrayList<>();

		if (beerFilterDto.getName() != null) {
			Predicate namePredicate = builder.equal(root.get(ATTR_NAME), beerFilterDto.getName());
			predicateList.add(namePredicate);
		}

		if (beerFilterDto.getDescription() != null) {
			Predicate descriptionPredicate = builder.like(root.get(ATTR_DESCRIPTION), beerFilterDto.getDescription());
			predicateList.add(descriptionPredicate);
		}

		if (beerFilterDto.getMinGraduation() != null && beerFilterDto.getMaxGraduation() != null) {
			Predicate graduationPredicate = builder.between(root.get(ATTR_GRADUATION), beerFilterDto.getMinGraduation(), beerFilterDto.getMaxGraduation());
			predicateList.add(graduationPredicate);
		} else if (beerFilterDto.getMinGraduation() != null) {
			Predicate graduationPredicate = builder.greaterThan(root.get(ATTR_GRADUATION), beerFilterDto.getMinGraduation());
			predicateList.add(graduationPredicate);
		} else if (beerFilterDto.getMaxGraduation() != null) {
			Predicate graduationPredicate = builder.lessThan(root.get(ATTR_GRADUATION), beerFilterDto.getMaxGraduation());
			predicateList.add(graduationPredicate);
		}

		if (beerFilterDto.getBeerTypeId() != null) {
			Predicate beerTypeIdPredicate = builder.equal(root.get(ATTR_BEER_TYPE), beerFilterDto.getBeerTypeId());
			predicateList.add(beerTypeIdPredicate);
		}

		if (beerFilterDto.getManufacturerId() != null) {
			Predicate manufacturerIdPredicate = builder.equal(root.get(ATTR_MANUFACTURER), beerFilterDto.getManufacturerId());
			predicateList.add(manufacturerIdPredicate);
		}

		List<Sort.Order> orders = pageable.getSort().get().collect(Collectors.toList());
		List<Order> criteriaSortOrderList = new ArrayList<>();
		for (Sort.Order sortOrder : orders) {
			Order criteriaSortOrder;
			if (sortOrder.isAscending()) {
				criteriaSortOrder = builder.asc(root.get(sortOrder.getProperty()));
			} else {
				criteriaSortOrder = builder.desc(root.get(sortOrder.getProperty()));
			}
			criteriaSortOrderList.add(criteriaSortOrder);
		}

		query.orderBy(criteriaSortOrderList);


		//count query
		TypedQuery<Beer> typedQuery = em.createQuery(query.select(root).where(predicateList.toArray(new Predicate[0])));
		int totalBeerListCount = typedQuery.getResultList().size();

		int firstResult = (pageable.getPageNumber()) * pageable.getPageSize();
		TypedQuery<Beer> paginatedTypedQuery = typedQuery.setFirstResult(firstResult).setMaxResults(pageable.getPageSize());
		List<Beer> paginatedBeerList = paginatedTypedQuery.getResultList();
		List<BeerDto> beerDtoList = beerMapper.beerListToBeerDtoList(paginatedBeerList);
		Page<BeerDto> beerDtoPage = new PageImpl<>(beerDtoList, pageable, totalBeerListCount);

		LOGGER.debug("<< findAllByParams() paginatedBeerList {}", paginatedBeerList);
		return beerDtoPage;
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

		BeerDto beerDtoPersisted = createOrUpdateBeer(beerDto);

		LOGGER.debug("<< create() beerDtoPersisted {}", beerDtoPersisted);
		return beerDtoPersisted;
	}

	@Override
	public BeerDto update(BeerDto beerDto) {
		LOGGER.debug(">> update() beerDto {}", beerDto);

		BeerDto beerDtoPersisted = createOrUpdateBeer(beerDto);

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

	// region Private methods

	private BeerDto createOrUpdateBeer(BeerDto beerDto) {
		LOGGER.trace(">> createOrUpdateBeer() beerDto {}", beerDto);

		Beer beerToPersist = beerMapper.beerDtoToBeer(beerDto);
		Beer beerPersisted =  jpaBeerRepository.save(beerToPersist);
		BeerDto beerDtoPersisted = beerMapper.beerToBeerDto(beerPersisted);

		LOGGER.trace("<< createOrUpdateBeer() beerDtoPersisted {}", beerDtoPersisted);
		return beerDtoPersisted;
	}

	// endregion

}
