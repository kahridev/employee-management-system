package com.mustafakahriman.employeemanagementsystem.controller.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateEmployeeRequest {
    @NotBlank(message = "Name cannot be blank")
    private String name;
    @NotBlank(message = "Surname cannot be blank")
    private String surname;
    private String position;
    @NotNull(message = "Salary cannot be blank")
    private BigDecimal salary;
    private Long departmentId;
}
