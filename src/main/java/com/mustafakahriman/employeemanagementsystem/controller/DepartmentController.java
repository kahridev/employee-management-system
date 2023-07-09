package com.mustafakahriman.employeemanagementsystem.controller;

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

import com.mustafakahriman.employeemanagementsystem.controller.request.CreateDepartmentRequest;
import com.mustafakahriman.employeemanagementsystem.controller.request.DepartmentSearchRequest;
import com.mustafakahriman.employeemanagementsystem.controller.request.UpdateDepartmentRequest;
import com.mustafakahriman.employeemanagementsystem.controller.response.BaseGetResponse;
import com.mustafakahriman.employeemanagementsystem.entity.Department;
import com.mustafakahriman.employeemanagementsystem.service.DepartmentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/department")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping
    public BaseGetResponse<Department> searchDepartments(
            @RequestParam(value = "departmentName", required = false) String departmentName,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "5") Integer size,
            @RequestParam(value = "sortBy", required = false, defaultValue = "asc") String sortBy,
            @RequestParam(value = "sortField", required = false, defaultValue = "id") String sortField) {
        var request = new DepartmentSearchRequest();
        request.setDepartmentName(departmentName);
        request.setPage(page);
        request.setSize(size);
        request.setSortBy(sortBy);
        request.setSortField(sortField);
        var result = departmentService.getDepartments(request);
        return new BaseGetResponse<Department>(result);
    }

    @PostMapping
    public ResponseEntity<Department> createDepartment(@RequestBody @Valid CreateDepartmentRequest request) {
        var createdDepartment = departmentService.createDepartment(request.getDepartmentName());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDepartment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable("id") Long id,
            @RequestBody @Valid UpdateDepartmentRequest request) {
        var updatedDepartment = departmentService.updateDepartment(id, request.getDepartmentName());
        return updatedDepartment != null ? ResponseEntity.ok(updatedDepartment) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }
}
