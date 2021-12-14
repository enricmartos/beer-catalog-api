package org.emartos.beer.catalog.api.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageableResponseDto<T> {

	private List<T> elementList;
	private int currentPage;
	private long totalItems;
	private int totalPages;

}
