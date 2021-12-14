/**
 * Copyright (c) 2021
 *
 * Author: enricmartos
 */
package org.emartos.beer.catalog.api.repository.repository.jpa;

import org.emartos.beer.catalog.api.repository.entity.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaManufacturerRepository extends JpaRepository<Manufacturer, Long> {

	int deleteManufacturerById(Long id);

}
