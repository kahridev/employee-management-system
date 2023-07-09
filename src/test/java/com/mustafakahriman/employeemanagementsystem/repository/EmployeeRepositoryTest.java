package com.mustafakahriman.employeemanagementsystem.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;

import com.mustafakahriman.employeemanagementsystem.controller.request.EmployeeSearchRequest;
import com.mustafakahriman.employeemanagementsystem.entity.Department;
import com.mustafakahriman.employeemanagementsystem.entity.Employee;
import com.mustafakahriman.employeemanagementsystem.specification.EmployeeSpecification;

@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void testEmployeeSpecification() {
        Department department = new Department();
        department.setDepartmentName("Engineering");

        Employee employee1 = new Employee();
        employee1.setName("Benjamin");
        employee1.setSurname("Franklin");
        employee1.setPosition("Developer");
        employee1.setSalary(BigDecimal.valueOf(5000));
        employee1.setDepartment(department);
        employeeRepository.save(employee1);

        Employee employee2 = new Employee();
        employee2.setName("John");
        employee2.setSurname("Smith");
        employee2.setPosition("Manager");
        employee2.setSalary(BigDecimal.valueOf(7000));
        employee2.setDepartment(department);
        employeeRepository.save(employee2);

        EmployeeSearchRequest searchRequest = new EmployeeSearchRequest();
        searchRequest.setName("John");
        searchRequest.setSurname("Smith");
        searchRequest.setPosition("Manager");
        searchRequest.setMaxSalary(BigDecimal.valueOf(8000));
        searchRequest.setMinSalary(BigDecimal.valueOf(6000));
        searchRequest.setPage(0);
        searchRequest.setSize(10);
        searchRequest.setSortBy("asc");
        searchRequest.setSortField("id");

        EmployeeSpecification specification = new EmployeeSpecification(searchRequest);

        Page<Employee> employees = employeeRepository.findAll(specification, searchRequest.getPageRequest());

        assertThat(employees).hasSize(1);
        assertThat(employees.getContent().get(0).getName()).isEqualTo("John");
        assertThat(employees.getContent().get(0).getPosition()).isEqualTo("Manager");
    }
}
