package com.mustafakahriman.employeemanagementsystem.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;

import com.mustafakahriman.employeemanagementsystem.controller.request.DepartmentSearchRequest;
import com.mustafakahriman.employeemanagementsystem.entity.Department;
import com.mustafakahriman.employeemanagementsystem.specification.DepartmentSpecification;

@DataJpaTest
public class DepartmentRepositoryTest {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    public void testEmployeeSpecification() {
        DepartmentSearchRequest searchRequest = new DepartmentSearchRequest();
        searchRequest.setDepartmentName("Fin");
        searchRequest.setPage(0);
        searchRequest.setSize(5);
        searchRequest.setSortBy("desc");
        searchRequest.setSortField("id");

        DepartmentSpecification specification = new DepartmentSpecification(searchRequest);

        Page<Department> departments = departmentRepository.findAll(specification, searchRequest.getPageRequest());

        assertThat(departmentRepository.count()).isEqualTo(6);
        assertThat(departments).hasSize(1);
        assertThat(departments.getContent().get(0).getDepartmentName()).isEqualTo("Finance");
    }
}
