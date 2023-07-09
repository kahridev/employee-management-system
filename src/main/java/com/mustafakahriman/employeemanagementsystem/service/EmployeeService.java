package com.mustafakahriman.employeemanagementsystem.service;

import java.util.NoSuchElementException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mustafakahriman.employeemanagementsystem.controller.request.CreateEmployeeRequest;
import com.mustafakahriman.employeemanagementsystem.controller.request.EmployeeSearchRequest;
import com.mustafakahriman.employeemanagementsystem.controller.request.UpdateEmployeeRequest;
import com.mustafakahriman.employeemanagementsystem.entity.Department;
import com.mustafakahriman.employeemanagementsystem.entity.Employee;
import com.mustafakahriman.employeemanagementsystem.repository.EmployeeRepository;
import com.mustafakahriman.employeemanagementsystem.specification.EmployeeSpecification;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentService departmentService;

    public Page<Employee> getEmployees(EmployeeSearchRequest employeeSearchRequest) {
        EmployeeSpecification employeeSpecification = new EmployeeSpecification(employeeSearchRequest);
        Pageable pageRequest = employeeSearchRequest.getPageRequest();
        return employeeRepository.findAll(employeeSpecification, pageRequest);
    }

    public Employee createEmployee(CreateEmployeeRequest request) {
        Department department = departmentService.getDepartment(request.getDepartmentId());
        var employee = Employee.builder().department(department).name(request.getName()).position(request.getPosition())
                .salary(request.getSalary()).surname(request.getSurname()).build();
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Long id, UpdateEmployeeRequest request) {
        return employeeRepository.findById(id)
                .map(employee -> {
                    Department department = departmentService.getDepartment(request.getDepartmentId());
                    employee.setName(request.getName());
                    employee.setPosition(request.getPosition());
                    employee.setSalary(request.getSalary());
                    employee.setSurname(request.getSurname());
                    employee.setDepartment(department);
                    return employeeRepository.save(employee);
                }).orElseThrow(() -> new NoSuchElementException(String.format("Employee with id '%d' not found", id)));
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}
