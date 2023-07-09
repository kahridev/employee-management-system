package com.mustafakahriman.employeemanagementsystem.specification;

import java.util.ArrayList;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.mustafakahriman.employeemanagementsystem.controller.request.DepartmentSearchRequest;
import com.mustafakahriman.employeemanagementsystem.entity.Department;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class DepartmentSpecification implements Specification<Department> {

	private DepartmentSearchRequest request;

	public DepartmentSpecification(DepartmentSearchRequest departmentSearchRequest) {
		this.request = departmentSearchRequest;
	}

	@Override
	public Predicate toPredicate(Root<Department> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		var predicates = new ArrayList<Predicate>();
		if (StringUtils.hasText(request.getDepartmentName())) {
			var departmentName = criteriaBuilder.like(root.get("departmentName"),
					"%" + request.getDepartmentName() + "%");
			predicates.add(departmentName);
		}
		return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
	}
}
