/**
 * Copyright (c) 2021
 *
 * Author: enricmartos
 */
package org.emartos.beer.catalog.api.repository.repository.jpa;

import org.emartos.beer.catalog.api.repository.entity.Beer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaBeerRepository extends JpaRepository<Beer, Long> {

	Page<Beer> findAllByName(String name, Pageable pageable);

	int deleteBeerById(Long id);

}
