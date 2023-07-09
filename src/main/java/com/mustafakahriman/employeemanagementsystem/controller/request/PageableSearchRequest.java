package com.mustafakahriman.employeemanagementsystem.controller.request;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public abstract class PageableSearchRequest {
	private static final String SORT_BY_DESC = "desc";

	private Integer page;
	private Integer size;
	private String sortBy;
	private String sortField;

	public Pageable getPageRequest() {
		var sort = SORT_BY_DESC.equals(sortBy) ? Sort.by(Sort.Order.desc(sortField))
				: Sort.by(Sort.Order.asc(sortField));
		return PageRequest.of(page, size, sort);
	}
}