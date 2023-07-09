package com.mustafakahriman.employeemanagementsystem.service;

import java.util.NoSuchElementException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mustafakahriman.employeemanagementsystem.controller.request.DepartmentSearchRequest;
import com.mustafakahriman.employeemanagementsystem.entity.Department;
import com.mustafakahriman.employeemanagementsystem.repository.DepartmentRepository;
import com.mustafakahriman.employeemanagementsystem.specification.DepartmentSpecification;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public Department getDepartment(Long id) {
        return departmentRepository.findById(id).orElse(null);
    }

    public Page<Department> getDepartments(DepartmentSearchRequest departmentSearchRequest) {
        DepartmentSpecification deparmentSpecification = new DepartmentSpecification(departmentSearchRequest);
        Pageable pageRequest = departmentSearchRequest.getPageRequest();
        return departmentRepository.findAll(deparmentSpecification, pageRequest);
    }

    public Department createDepartment(String departmentName) {
        var department = new Department();
        department.setDepartmentName(departmentName);
        return departmentRepository.save(department);
    }

    public Department updateDepartment(Long id, String newDepartmentName) {
        return departmentRepository.findById(id)
                .map(department -> {
                    department.setDepartmentName(newDepartmentName);
                    return departmentRepository.save(department);
                })
                .orElseThrow(() -> new NoSuchElementException(String.format("Deparment with id: '%d' not found", id)));
    }

    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }
}
