package com.mustafakahriman.employeemanagementsystem.controller.request;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class EmployeeSearchRequest extends PageableSearchRequest {
    private Long id;
    private String name;
    private String surname;
    private String position;
    private BigDecimal minSalary;
    private BigDecimal maxSalary;
    private String departmentName;
}
