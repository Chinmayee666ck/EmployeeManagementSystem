package com.example.employeemanagementsystem;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee addEmployee(Employee employee) {
        employee.setId(null);
        return employeeRepository.save(employee);
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElseThrow(()->new RuntimeException("Employee not found with id: " + id));
    }

    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        Optional<Employee> result = employeeRepository.findById(id);

        if (result.isPresent()) {
            Employee employee = result.get();
            employee.setName(updatedEmployee.getName());
            employee.setEmail(updatedEmployee.getEmail());
            employee.setSalary(updatedEmployee.getSalary());

            return employeeRepository.save(employee);
        }

        throw new RuntimeException("Employee not found with id: " + id);
    }

    public Employee deleteEmployee(Long id) {
        Optional<Employee> result = employeeRepository.findById(id);

        if (result.isPresent()) {
            Employee employee = result.get();
            employeeRepository.delete(employee);
            return employee;
        }

        throw new RuntimeException("Employee not found with id: " + id);
    }
    public Page<Employee>
    getAllEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }
}