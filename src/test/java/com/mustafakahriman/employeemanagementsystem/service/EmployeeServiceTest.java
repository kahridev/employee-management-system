package com.mustafakahriman.employeemanagementsystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mustafakahriman.employeemanagementsystem.controller.request.CreateEmployeeRequest;
import com.mustafakahriman.employeemanagementsystem.controller.request.EmployeeSearchRequest;
import com.mustafakahriman.employeemanagementsystem.controller.request.UpdateEmployeeRequest;
import com.mustafakahriman.employeemanagementsystem.entity.Department;
import com.mustafakahriman.employeemanagementsystem.entity.Employee;
import com.mustafakahriman.employeemanagementsystem.repository.EmployeeRepository;
import com.mustafakahriman.employeemanagementsystem.specification.EmployeeSpecification;

public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DepartmentService departmentService;

    @InjectMocks
    private EmployeeService employeeService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void whenSearchEmployee_withAllFields_thenReturnResult() {
        EmployeeSearchRequest searchRequest = prepareEmployeeSearchRequest();
        Page<Employee> expectedPage = Page.empty();

        when(employeeRepository.findAll(any(EmployeeSpecification.class), any(Pageable.class)))
                .thenReturn(expectedPage);

        Page<Employee> actualPage = employeeService.getEmployees(searchRequest);

        assertEquals(0, actualPage.getContent().size());
        verify(employeeRepository).findAll(any(EmployeeSpecification.class), any(Pageable.class));
    }

    @Test
    public void whenCreateEmployee_thenCreateSuccessfully() {
        CreateEmployeeRequest request = prepareCreateEmployeeRequest();
        Employee employee = prepareEmployee();

        when(departmentService.getDepartment(eq(request.getDepartmentId()))).thenReturn(employee.getDepartment());
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        var response = employeeService.createEmployee(request);

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(response.getId()).isEqualTo(employee.getId());
        softAssertions.assertThat(response.getSalary()).isEqualTo(employee.getSalary());
        softAssertions.assertThat(response.getName()).isEqualTo(employee.getName());
        softAssertions.assertThat(response.getPosition()).isEqualTo(employee.getPosition());
        softAssertions.assertThat(response.getSurname()).isEqualTo(employee.getSurname());
        softAssertions.assertThat(response.getDepartment().getDepartmentName())
                .isEqualTo(employee.getDepartment().getDepartmentName());
        softAssertions.assertAll();

        verify(departmentService).getDepartment(1L);
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    public void whenEmployeeLeveledUp_thenUpdateSuccessfully() {
        var oldEmployee = prepareEmployee();
        var request = prepareUpdateEmployeeRequest();

        var newEmployee = new Employee();
        newEmployee.setDepartment(prepareDepartment());
        newEmployee.setName(oldEmployee.getName());
        newEmployee.setPosition("Director");
        newEmployee.setSalary(new BigDecimal(12000));
        newEmployee.setSurname(oldEmployee.getSurname());
        newEmployee.setId(1L);

        var newDepartment = new Department();
        newDepartment.setId(2L);
        newDepartment.setDepartmentName("Operations");

        when(employeeRepository.findById(oldEmployee.getId())).thenReturn(Optional.of(oldEmployee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(newEmployee);
        when(departmentService.getDepartment(any(Long.class))).thenReturn(newDepartment);

        var response = employeeService.updateEmployee(oldEmployee.getId(), request);

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(response.getId()).isEqualTo(oldEmployee.getId());
        softAssertions.assertThat(response.getSalary()).isEqualTo(oldEmployee.getSalary());
        softAssertions.assertThat(response.getName()).isEqualTo(oldEmployee.getName());
        softAssertions.assertThat(response.getPosition()).isEqualTo(oldEmployee.getPosition());
        softAssertions.assertThat(response.getSurname()).isEqualTo(oldEmployee.getSurname());
        softAssertions.assertThat(response.getDepartment().getDepartmentName()).isEqualTo("Sales");
        softAssertions.assertAll();

        verify(employeeRepository).findById(oldEmployee.getId());
        verify(employeeRepository).save(any(Employee.class));
    }

    @Test
    public void testUpdateEmployee_NotFound() {
        Long employeeId = 1L;
        var updateRequest = prepareUpdateEmployeeRequest();

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> employeeService.updateEmployee(employeeId, updateRequest));

        verify(employeeRepository, times(1)).findById(employeeId);
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    public void testDeleteEmployee() {
        Long employeeId = 1L;

        doNothing().when(employeeRepository).deleteById(employeeId);

        employeeService.deleteEmployee(employeeId);

        verify(employeeRepository).deleteById(employeeId);
    }

    @Test
    public void testDeleteEmployee_Exception() {
        Long employeeId = 1L;

        doThrow(RuntimeException.class).when(employeeRepository).deleteById(employeeId);

        boolean exceptionOccured = false;
        try {
            employeeService.deleteEmployee(employeeId);
        } catch (Exception e) {
            exceptionOccured = true;
        }

        assertTrue(exceptionOccured);
        verify(employeeRepository).deleteById(employeeId);
    }

    private static EmployeeSearchRequest prepareEmployeeSearchRequest() {
        EmployeeSearchRequest request = new EmployeeSearchRequest();
        request.setDepartmentName(null);
        request.setMaxSalary(new BigDecimal(5000));
        request.setMinSalary(new BigDecimal(12000));
        request.setName("Ali");
        request.setPage(0);
        request.setPosition("Officer");
        request.setSize(5);
        request.setSortBy("asc");
        request.setSortField("id");
        request.setSurname("Kahraman");
        return request;
    }

    private static CreateEmployeeRequest prepareCreateEmployeeRequest() {
        var request = new CreateEmployeeRequest();
        request.setDepartmentId(1L);
        request.setName("Ali");
        request.setPosition("Manager");
        request.setSalary(new BigDecimal(10000));
        request.setSurname("Kahraman");
        return request;
    }

    private static UpdateEmployeeRequest prepareUpdateEmployeeRequest() {
        UpdateEmployeeRequest request = new UpdateEmployeeRequest();
        request.setDepartmentId(2L);
        request.setName("Ali");
        request.setPosition("Director");
        request.setSalary(new BigDecimal(12000));
        request.setSurname("Kahraman");
        return request;
    }

    private static Department prepareDepartment() {
        Department department = new Department();
        department.setId(1L);
        department.setDepartmentName("Sales");
        return department;
    }

    private static Employee prepareEmployee() {
        Department department = prepareDepartment();

        Employee employee = new Employee();
        employee.setDepartment(department);
        employee.setId(1L);
        employee.setName("Ali");
        employee.setPosition("Manager");
        employee.setSalary(new BigDecimal(10000));
        employee.setSurname("Kahraman");

        return employee;
    }
}