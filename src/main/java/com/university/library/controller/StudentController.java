package com.university.library.controller;

import com.university.library.entity.BookLoan;
import com.university.library.service.LoanService;
import com.university.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private LoanService loanService;
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentProfile(@PathVariable Long id) {
        return userService.findById(id)
                .map(user -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("id", user.getId());
                    response.put("username", user.getUsername());
                    response.put("active", user.isActive());
                    response.put("userType", user.getUserType());
                    
                    // اگر کاربر دانشجو است، اطلاعات اضافه را برگردان
                    if (user instanceof com.university.library.entity.Student) {
                        com.university.library.entity.Student student = 
                            (com.university.library.entity.Student) user;
                        response.put("studentId", student.getStudentId());
                    }
                    
                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStudentStatus(@PathVariable Long id, 
                                                 @RequestBody Map<String, Boolean> request) {
        boolean active = request.getOrDefault("active", true);
        
        return userService.findById(id)
                .map(user -> {
                    user.setActive(active);
                    userService.saveUser(user);
                    
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("message", "Student status updated successfully");
                    response.put("active", active);
                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/{id}/borrow-history")
    public ResponseEntity<List<BookLoan>> getBorrowHistory(@PathVariable Long id) {
        return userService.findById(id)
                .map(user -> {
                    List<BookLoan> history = loanService.getStudentLoanHistory(user);
                    return ResponseEntity.ok(history);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}