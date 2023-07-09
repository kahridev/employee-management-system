package com.mustafakahriman.employeemanagementsystem.specification;

import java.util.ArrayList;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.mustafakahriman.employeemanagementsystem.controller.request.EmployeeSearchRequest;
import com.mustafakahriman.employeemanagementsystem.entity.Employee;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class EmployeeSpecification implements Specification<Employee> {

	private EmployeeSearchRequest request;

	public EmployeeSpecification(EmployeeSearchRequest employeeSearchRequest) {
		this.request = employeeSearchRequest;
	}

	@Override
	public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		var predicates = new ArrayList<Predicate>();

		if (request.getId() != null) {
			var id = criteriaBuilder.equal(root.get("id"), request.getId());
			predicates.add(id);
		}

		if (StringUtils.hasText(request.getName())) {
			var name = criteriaBuilder.equal(root.get("name"), request.getName());
			predicates.add(name);
		}

		if (StringUtils.hasText(request.getSurname())) {
			var surname = criteriaBuilder.equal(root.get("surname"), request.getSurname());
			predicates.add(surname);
		}

		if (StringUtils.hasText(request.getPosition())) {
			var position = criteriaBuilder.equal(root.get("position"), request.getPosition());
			predicates.add(position);
		}

		if (request.getMinSalary() != null && request.getMaxSalary() != null) {
			var salary = criteriaBuilder.between(root.get("salary"), request.getMinSalary(), request.getMaxSalary());
			predicates.add(salary);
		} else if (request.getMinSalary() != null) {
			var salary = criteriaBuilder.greaterThanOrEqualTo(root.get("salary"), request.getMinSalary());
			predicates.add(salary);
		} else if (request.getMaxSalary() != null) {
			var salary = criteriaBuilder.lessThanOrEqualTo(root.get("salary"), request.getMaxSalary());
			predicates.add(salary);
		}

		if (StringUtils.hasText(request.getDepartmentName())) {
			var department = root.join("department", JoinType.INNER);
			Predicate departmentName = criteriaBuilder.equal(department.get("departmentName"),
					request.getDepartmentName());
			predicates.add(departmentName);
		}
		return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
	}
}
