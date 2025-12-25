package com.university.library.controller;

import com.university.library.entity.Employee;
import com.university.library.repository.EmployeeRepository;
import com.university.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    @PostMapping("/employees")
    public ResponseEntity<?> createEmployee(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");
        String employeeId = request.get("employeeId");
        String position = request.get("position");
        
        if (username == null || password == null) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Username and password are required");
            return ResponseEntity.badRequest().body(response);
        }
        
        if (userRepository.existsByUsername(username)) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Username already exists");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
        
        Employee employee = new Employee(username, password, employeeId, position);
        Employee savedEmployee = employeeRepository.save(employee);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Employee created successfully");
        response.put("employeeId", savedEmployee.getId());
        response.put("username", savedEmployee.getUsername());
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return ResponseEntity.ok(employees);
    }
}