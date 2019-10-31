package com.codegym.cms.controller;

import com.codegym.cms.model.Department;
import com.codegym.cms.model.Employee;
import com.codegym.cms.service.DepartmentService;
import com.codegym.cms.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class EmployeeController {
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private EmployeeService employeeService;
    @ModelAttribute("department")
    public Iterable<Department> departments(){
        return departmentService.findAll();
    }
    @GetMapping("/create-employee")
    public ModelAndView showCreateForm(){
        ModelAndView modelAndView = new ModelAndView("/employee/create");
        modelAndView.addObject("employee",new Employee());
        return modelAndView;
    }
    @PostMapping("/create-employee")
    public ModelAndView saveEmployee(@ModelAttribute("employee")Employee employee){
        employeeService.save(employee);
        ModelAndView modelAndView = new ModelAndView("/employee/create");
        modelAndView.addObject("employee",new Employee());
        modelAndView.addObject("message","New Employee created successfully");
        return modelAndView;
    }
    @GetMapping("/employees")
    public ModelAndView listEmployees(@RequestParam("s")Optional<String> s, @PageableDefault(value = 3)Pageable pageable){
        Page<Employee> employees;
        if(s.isPresent()){
            employees = employeeService.findAllByNameDepartment(s.get(),pageable);
        }else {
            employees = employeeService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("/employee/list");
        modelAndView.addObject("employee",employees);
        return modelAndView;
    }
    @GetMapping("/edit-employee/{id}")
    public ModelAndView showEditForm(@PathVariable Long id){
        Employee employee = employeeService.findById(id);
        if(employee != null){
            ModelAndView modelAndView = new ModelAndView("/employee/edit");
            modelAndView.addObject("employee",employee);
            return modelAndView;
        }else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }
    @PostMapping("/edit-employee")
    public ModelAndView updateEmployee(@ModelAttribute("employee")Employee employee){
        employeeService.save(employee);
        ModelAndView modelAndView = new ModelAndView("/employee/edit");
        modelAndView.addObject("employee",employee);
        modelAndView.addObject("message","Employee updated successfully");
                return modelAndView;
    }
    @GetMapping("/delete-employee")
    public ModelAndView showDeleteForm(@PathVariable Long id){
        Employee employee = employeeService.findById(id);
        if(employee !=null){
            ModelAndView modelAndView = new ModelAndView("/employee/delete");
            modelAndView.addObject("employee",employee);
            return modelAndView;
        }else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }
    @PostMapping("/delete-employee")
    public String deleteCustomer(@ModelAttribute("employee")Employee employee){
        employeeService.remove(employee.getId());
        return "redirect:employees";
    }
}
