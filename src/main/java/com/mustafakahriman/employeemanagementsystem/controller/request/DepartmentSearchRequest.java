package com.mustafakahriman.employeemanagementsystem.controller.request;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class DepartmentSearchRequest extends PageableSearchRequest {
    private String departmentName;
}
