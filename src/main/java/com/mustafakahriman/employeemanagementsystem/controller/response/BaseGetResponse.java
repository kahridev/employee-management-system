package com.mustafakahriman.employeemanagementsystem.controller.response;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseGetResponse<T> extends BaseResponse {
    private Integer page;
    private Integer size;
    private Integer totalPages;
    private Long totalElements;
    private List<T> content;

    public BaseGetResponse(Page<T> result) {
        super();
        this.page = result.getPageable().getPageNumber();
        this.size = result.getPageable().getPageSize();
        this.totalElements = result.getTotalElements();
        this.totalPages = result.getTotalPages();
        this.content = result.getContent();
    }
}
