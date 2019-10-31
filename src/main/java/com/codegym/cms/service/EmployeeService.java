package com.codegym.cms.service;

import com.codegym.cms.model.Department;
import com.codegym.cms.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeService {
    Page<Employee> findAll(Pageable pageable);
    Page<Employee> findAllByNameDepartment (String name, Pageable pageable);
    Employee findById(Long id);
    void save(Employee employee);
    void remove(Long id);
    Iterable<Employee> findAllByDepartment(Department department);
}
