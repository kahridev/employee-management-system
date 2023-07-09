package com.mustafakahriman.employeemanagementsystem.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateDepartmentRequest {
    @NotBlank(message = "Department Name cannot be blank")
    private String departmentName;
}
