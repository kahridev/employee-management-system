package com.mustafakahriman.employeemanagementsystem.controller.response;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class BaseResponse {
    public BaseResponse() {
        this.success = true;
        this.message = StringUtils.EMPTY;
        this.errors = null;
    }

    public boolean success;
    public List<String> errors;
    public String message;
}