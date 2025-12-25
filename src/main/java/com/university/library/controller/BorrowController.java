package com.university.library.controller;

import com.university.library.entity.BookLoan;
import com.university.library.service.BookService;
import com.university.library.service.LoanService;
import com.university.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/borrow")
public class BorrowController {
    
    @Autowired
    private LoanService loanService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private BookService bookService;
    
    @PostMapping("/request")
    public ResponseEntity<?> requestBookLoan(@RequestBody Map<String, Object> request) {
        try {
            Long userId = Long.parseLong(request.get("userId").toString());
            Long bookId = Long.parseLong(request.get("bookId").toString());
            LocalDate startDate = LocalDate.parse(request.get("startDate").toString());
            LocalDate endDate = LocalDate.parse(request.get("endDate").toString());
            
            var user = userService.findById(userId);
            var book = bookService.findById(bookId);
            
            if (user.isEmpty() || book.isEmpty()) {
                Map<String, String> response = new HashMap<>();
                response.put("error", "User or book not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            
            BookLoan loan = loanService.requestBookLoan(user.get(), book.get(), startDate, endDate);
            return ResponseEntity.status(HttpStatus.CREATED).body(loan);
            
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    
    @GetMapping("/requests/pending")
    public ResponseEntity<List<BookLoan>> getPendingRequests() {
        List<BookLoan> pendingLoans = loanService.getPendingLoans();
        return ResponseEntity.ok(pendingLoans);
    }
    
    @PostMapping("/requests/{id}/approve")
    public ResponseEntity<?> approveLoanRequest(@PathVariable Long id) {
        try {
            loanService.approveLoan(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Loan request approved successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    
    @PostMapping("/requests/{id}/reject")
    public ResponseEntity<?> rejectLoanRequest(@PathVariable Long id) {
        // Implementation for reject
        Map<String, String> response = new HashMap<>();
        response.put("message", "Loan request rejected (placeholder)");
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/{id}/return")
    public ResponseEntity<?> returnBook(@PathVariable Long id) {
        try {
            loanService.returnBook(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Book returned successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}