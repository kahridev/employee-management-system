package com.mustafakahriman.employeemanagementsystem.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DEPARTMENT")
@Builder
public class Department extends BaseEntity {
    @Column(name = "DEPARTMENT_NAME", unique = true, nullable = false)
    private String departmentName;

    @OneToMany(mappedBy = "department")
    @JsonBackReference
    private List<Employee> employees;
}