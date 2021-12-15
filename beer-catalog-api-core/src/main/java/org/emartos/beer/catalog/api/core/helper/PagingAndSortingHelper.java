package org.emartos.beer.catalog.api.core.helper;


import org.emartos.beer.catalog.api.core.dto.PageableResponseDto;
import org.emartos.beer.catalog.api.core.exception.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class PagingAndSortingHelper {

	private PagingAndSortingHelper() {
		// Default private constructor
	}

	public static Pageable getPageableRequest(int currentPage, int pageSize, String[] sort) throws BadRequestException {
		List<Sort.Order> orders = new ArrayList<>();

		if (sort[0].contains(",")) {
			for (String sortOrder : sort) {
				String[] sortSplit = sortOrder.split(",");
				orders.add(new Sort.Order(getSortDirection(sortSplit[1]), sortSplit[0]));
			}
		} else if (sort.length == 2) {
			orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
		} else {
			throw new BadRequestException("Invalid format. The correct format is {sortParam},{sortDirection(asc/desc)}");
		}

		return PageRequest.of(currentPage, pageSize, Sort.by(orders));
	}

	public static <T> PageableResponseDto<T> buildPageableResponseDto(Page<T> page) {
		return new PageableResponseDto<>(page.getContent(), page.getNumber(), page.getTotalElements(), page.getTotalPages());
	}

	// region Private methods

	private static Sort.Direction getSortDirection(String sortDirectionString) throws BadRequestException {
		if (sortDirectionString.equals("asc")) {
			return Sort.Direction.ASC;
		} else if (sortDirectionString.equals("desc")) {
			return Sort.Direction.DESC;
		} else {
			throw new BadRequestException(String.format("Sort direction %s is invalid. It must be either \"asc\" or \"desc\"", sortDirectionString));
		}
	}

}
