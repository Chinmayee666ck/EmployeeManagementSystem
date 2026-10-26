package com.example.employeemanagementsystem;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;


@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    @GetMapping
    public Page<Employee> getAllEmployees(@RequestParam(defaultValue = "0")
                                          int page,@RequestParam(defaultValue = "5")
                                          int size,@RequestParam(defaultValue = "id") String sortBy) {
        if(page < 0){
            throw new IllegalArgumentException("page number cannot be negative");
        }
        if(size<=0){
            throw new IllegalArgumentException("sizes should be greater than zero");
        }
        if(!sortBy.equals("id") && !sortBy.equals("name") && !sortBy.equals("email") && !sortBy.equals("salary")){
            throw new IllegalArgumentException("Invalid sort field. Use id, name, email, or salary.");
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending()
        );
        return
                employeeService.getAllEmployees(pageable);
    }
    @PostMapping
    public Employee addEmployee(@Valid@RequestBody Employee employee) {

        return employeeService.addEmployee(employee);
    }
    @GetMapping("/{id}")
    public Employee getEmployee(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }
    @PutMapping("/{id}")
    public String updateEmployee(@PathVariable Long id,@Valid
                                 @RequestBody Employee employee) {
        Employee updated = employeeService.updateEmployee(id, employee);
 if(updated!=null){
     return "Employee updated successfully";
 }
 return "Employee could not be updated";
    }
    @DeleteMapping("/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        Employee deleted = employeeService.deleteEmployee(id);
        if(deleted!=null){
            return "Employee deleted successfully";
        }
        return "Employee could not be deleted";
    }
}
