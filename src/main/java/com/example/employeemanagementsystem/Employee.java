package com.example.employeemanagementsystem;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message="Name must not be blank")
    private String name;

    @NotBlank(message="Email is required")
    @Email(message="Please enter a valid email")
    @Column(unique=true)
    private String email;

    @PositiveOrZero(message="Salary cannot be negative")
    private double salary;

    public Employee(){}

    public Employee(Long id, String name, String email,double salary) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.salary = salary;

    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public double getSalary() {
        return salary;
    }
    public void setSalary(double salary) {
        this.salary = salary;
    }
}
