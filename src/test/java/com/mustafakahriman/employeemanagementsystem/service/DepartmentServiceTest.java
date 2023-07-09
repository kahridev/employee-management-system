package com.mustafakahriman.employeemanagementsystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.mustafakahriman.employeemanagementsystem.controller.request.CreateDepartmentRequest;
import com.mustafakahriman.employeemanagementsystem.controller.request.DepartmentSearchRequest;
import com.mustafakahriman.employeemanagementsystem.controller.request.UpdateDepartmentRequest;
import com.mustafakahriman.employeemanagementsystem.entity.Department;
import com.mustafakahriman.employeemanagementsystem.repository.DepartmentRepository;
import com.mustafakahriman.employeemanagementsystem.specification.DepartmentSpecification;

public class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentService departmentService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void whenSearchDepartment_withAllFields_thenReturnResult() {
        DepartmentSearchRequest searchRequest = new DepartmentSearchRequest();
        searchRequest.setDepartmentName("Sales");
        searchRequest.setPage(0);
        searchRequest.setSize(1);
        searchRequest.setSortBy("asc");
        searchRequest.setSortField("id");

        Page<Department> expectedPage = new PageImpl<>(List.of(prepareDepartment()));

        when(departmentRepository.findAll(any(DepartmentSpecification.class), any(Pageable.class)))
                .thenReturn(expectedPage);

        Page<Department> actualPage = departmentService.getDepartments(searchRequest);

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(actualPage.getContent().size()).isEqualTo(1);
        softAssertions.assertThat(actualPage.getContent().get(0).getDepartmentName())
                .isEqualTo(searchRequest.getDepartmentName());
        softAssertions.assertAll();

        verify(departmentRepository).findAll(any(DepartmentSpecification.class), any(Pageable.class));
    }

    @Test
    public void whenCreateDepartment_thenCreateSuccessfully() {
        CreateDepartmentRequest request = new CreateDepartmentRequest();
        request.setDepartmentName("Human Resources");

        Department department = prepareDepartment();

        when(departmentRepository.save(any(Department.class))).thenReturn(department);

        var response = departmentService.createDepartment(request.getDepartmentName());

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(response.getId()).isEqualTo(department.getId());
        softAssertions.assertThat(response.getDepartmentName()).isEqualTo(department.getDepartmentName());
        softAssertions.assertAll();

        verify(departmentRepository, times(1)).save(any(Department.class));
    }

    @Test
    public void whenDepartmentLeveledUp_thenUpdateSuccessfully() {
        var oldDepartment = new Department();
        oldDepartment.setDepartmentName("Sales");
        oldDepartment.setId(1L);

        var newDepartment = new Department();
        newDepartment.setDepartmentName("Sales Operations");
        newDepartment.setId(1L);

        var request = new UpdateDepartmentRequest();
        request.setDepartmentName("Sales Operations");

        when(departmentRepository.findById(oldDepartment.getId())).thenReturn(Optional.of(oldDepartment));
        when(departmentRepository.save(any(Department.class))).thenReturn(newDepartment);

        var response = departmentService.updateDepartment(oldDepartment.getId(), request.getDepartmentName());

        assertEquals(response.getDepartmentName(), newDepartment.getDepartmentName());
        assertEquals(oldDepartment.getDepartmentName(), newDepartment.getDepartmentName());

        verify(departmentRepository).findById(oldDepartment.getId());
        verify(departmentRepository).save(any(Department.class));
    }

    @Test
    public void testUpdateDepartment_NotFound() {
        Long departmentId = 1L;
        var request = new UpdateDepartmentRequest();
        request.setDepartmentName("Sales Operations");

        when(departmentRepository.findById(departmentId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> departmentService.updateDepartment(departmentId, request.getDepartmentName()));

        verify(departmentRepository, times(1)).findById(departmentId);
        verify(departmentRepository, never()).save(any(Department.class));
    }

    @Test
    public void testDeleteDepartment() {
        Long departmentId = 1L;

        doNothing().when(departmentRepository).deleteById(departmentId);

        departmentService.deleteDepartment(departmentId);

        verify(departmentRepository).deleteById(departmentId);
    }

    @Test
    public void testDeleteDepartment_Exception() {
        Long departmentId = 1L;

        doThrow(RuntimeException.class).when(departmentRepository).deleteById(departmentId);

        departmentService.deleteDepartment(departmentId);

        verify(departmentRepository).deleteById(departmentId);
    }

    private static Department prepareDepartment() {
        Department department = new Department();
        department.setId(1L);
        department.setDepartmentName("Sales");
        return department;
    }
}