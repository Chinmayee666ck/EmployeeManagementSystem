package com.example.employeemanagementsystem;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    // Test 1: Retrieve employee by ID
    @Test
    void getEmployeeByIdShouldReturnEmployeeWhenFound() {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("Appu");
        employee.setEmail("appu@123.com");
        employee.setSalary(25000);

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        Employee result = employeeService.getEmployeeById(1L);

        assertNotNull(result);
        assertEquals("Appu", result.getName());
        assertEquals("appu@123.com", result.getEmail());
        assertEquals(25000, result.getSalary());

        verify(employeeRepository).findById(1L);
    }

    // Test 2: Employee not found by ID
    @Test
    void getEmployeeByIdShouldThrowExceptionWhenNotFound() {
        when(employeeRepository.findById(9999L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> employeeService.getEmployeeById(9999L)
        );

        assertEquals(
                "Employee not found with id: 9999",
                exception.getMessage()
        );
    }

    // Test 3: Retrieve all employees
    @Test
    void getAllEmployeesShouldReturnEmployeeList() {
        Employee employee1 = new Employee();
        employee1.setId(1L);
        employee1.setName("Appu");
        employee1.setEmail("appu@123.com");
        employee1.setSalary(25000);

        Employee employee2 = new Employee();
        employee2.setId(2L);
        employee2.setName("Pruthvi");
        employee2.setEmail("pruthvi@12gmail.com");
        employee2.setSalary(35000);

        when(employeeRepository.findAll())
                .thenReturn(List.of(employee1, employee2));

        List<Employee> result = employeeService.getAllEmployees();

        assertEquals(2, result.size());
        assertEquals("Appu", result.get(0).getName());
        assertEquals("Pruthvi", result.get(1).getName());

        verify(employeeRepository).findAll();
    }

    // Test 4: Add employee
    @Test
    void addEmployeeShouldSaveAndReturnEmployee() {
        Employee employee = new Employee();
        employee.setName("Chinmayee");
        employee.setEmail("chinmayee@example.com");
        employee.setSalary(30000);

        Employee savedEmployee = new Employee();
        savedEmployee.setId(10L);
        savedEmployee.setName("Chinmayee");
        savedEmployee.setEmail("chinmayee@example.com");
        savedEmployee.setSalary(30000);

        when(employeeRepository.save(employee))
                .thenReturn(savedEmployee);

        Employee result = employeeService.addEmployee(employee);

        assertEquals(10L, result.getId());
        assertEquals("Chinmayee", result.getName());
        assertEquals(30000, result.getSalary());

        verify(employeeRepository).save(employee);
    }
    // Test 5: Update existing employee
    @Test
    void updateEmployeeShouldUpdateAndReturnEmployee() {
        Employee existingEmployee = new Employee();
        existingEmployee.setId(1L);
        existingEmployee.setName("Appu");
        existingEmployee.setEmail("appu@123.com");
        existingEmployee.setSalary(25000);

        Employee updatedDetails = new Employee();
        updatedDetails.setName("Appu Updated");
        updatedDetails.setEmail("appu.updated@example.com");
        updatedDetails.setSalary(30000);

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(existingEmployee));

        when(employeeRepository.save(existingEmployee))
                .thenReturn(existingEmployee);

        Employee result =
                employeeService.updateEmployee(1L, updatedDetails);

        assertEquals("Appu Updated", result.getName());
        assertEquals("appu.updated@example.com", result.getEmail());
        assertEquals(30000, result.getSalary());

        verify(employeeRepository).findById(1L);
        verify(employeeRepository).save(existingEmployee);
    }

    // Test 6: Delete existing employee
    @Test
    void deleteEmployeeShouldDeleteAndReturnEmployee() {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("Appu");
        employee.setEmail("appu@123.com");
        employee.setSalary(25000);

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        Employee result = employeeService.deleteEmployee(1L);

        assertEquals(1L, result.getId());
        assertEquals("Appu", result.getName());

        verify(employeeRepository).findById(1L);
        verify(employeeRepository).delete(employee);
    }

    // Test 7: Update employee that does not exist
    @Test
    void updateEmployeeShouldThrowExceptionWhenEmployeeNotFound() {
        Employee updatedEmployee = new Employee();
        updatedEmployee.setName("New Name");
        updatedEmployee.setEmail("new@example.com");
        updatedEmployee.setSalary(30000);

        when(employeeRepository.findById(9999L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> employeeService.updateEmployee(9999L, updatedEmployee)
        );

        assertEquals(
                "Employee not found with id: 9999",
                exception.getMessage()
        );

        verify(employeeRepository).findById(9999L);
    }

    // Test 8: Delete employee that does not exist
    @Test
    void deleteEmployeeShouldThrowExceptionWhenEmployeeNotFound() {
        when(employeeRepository.findById(9999L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> employeeService.deleteEmployee(9999L)
        );

        assertEquals(
                "Employee not found with id: 9999",
                exception.getMessage()
        );

        verify(employeeRepository).findById(9999L);
    }
}