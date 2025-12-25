package com.university.library.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "employees")
@DiscriminatorValue("EMPLOYEE")
public class Employee extends User {
    
    @Column(name = "employee_id", unique = true)
    private String employeeId;
    
    @Column(name = "position")
    private String position;
    
    public Employee() {}
    
    public Employee(String username, String password) {
        super(username, password);
    }
    
    public Employee(String username, String password, String employeeId, String position) {
        super(username, password);
        this.employeeId = employeeId;
        this.position = position;
    }
    
    // Getters and Setters
    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }
    
    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }
    
    @Override
    public String getUserType() {
        return "EMPLOYEE";
    }
}