package com.mustafakahriman.employeemanagementsystem.controller;

import java.math.BigDecimal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mustafakahriman.employeemanagementsystem.controller.request.CreateEmployeeRequest;
import com.mustafakahriman.employeemanagementsystem.controller.request.EmployeeSearchRequest;
import com.mustafakahriman.employeemanagementsystem.controller.request.UpdateEmployeeRequest;
import com.mustafakahriman.employeemanagementsystem.controller.response.BaseGetResponse;
import com.mustafakahriman.employeemanagementsystem.entity.Employee;
import com.mustafakahriman.employeemanagementsystem.service.EmployeeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public BaseGetResponse<Employee> searchEmployees(
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "surname", required = false) String surname,
            @RequestParam(value = "position", required = false) String position,
            @RequestParam(value = "minSalary", required = false) BigDecimal minSalary,
            @RequestParam(value = "maxSalary", required = false) BigDecimal maxSalary,
            @RequestParam(value = "departmentName", required = false) String departmentName,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "5") Integer size,
            @RequestParam(value = "sortBy", required = false, defaultValue = "asc") String sortBy,
            @RequestParam(value = "sortField", required = false, defaultValue = "id") String sortField) {
        var request = new EmployeeSearchRequest();
        request.setDepartmentName(departmentName);
        request.setId(id);
        request.setMaxSalary(maxSalary);
        request.setMinSalary(minSalary);
        request.setName(name);
        request.setPage(page);
        request.setPosition(position);
        request.setSize(size);
        request.setSortBy(sortBy);
        request.setSortField(sortField);
        request.setSurname(surname);

        var result = employeeService.getEmployees(request);
        return new BaseGetResponse<Employee>(result);
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody @Valid CreateEmployeeRequest request) {
        var createdEmployee = employeeService.createEmployee(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") Long id,
            @RequestBody @Valid UpdateEmployeeRequest request) {
        var updatedEmployee = employeeService.updateEmployee(id, request);
        return updatedEmployee != null ? ResponseEntity.ok(updatedEmployee) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}
