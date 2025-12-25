package com.university.library.controller;

import com.university.library.entity.BookLoan;
import com.university.library.service.BookService;
import com.university.library.service.LoanService;
import com.university.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/stats")
public class StatsController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private BookService bookService;
    
    @Autowired
    private LoanService loanService;
    
    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> getSummary() {
        Map<String, Object> summary = new HashMap<>();
        
        summary.put("totalStudents", userService.getRegisteredStudentsCount());
        summary.put("totalBooks", bookService.getTotalBooksCount());
        summary.put("totalLoans", loanService.getTotalLoansCount());
        summary.put("activeLoans", loanService.getActiveLoansCount());
        summary.put("availableBooks", bookService.getAvailableBooks().size());
        
        return ResponseEntity.ok(summary);
    }
    
    @GetMapping("/borrows")
    public ResponseEntity<Map<String, Object>> getBorrowStats() {
        Map<String, Object> stats = new HashMap<>();
        List<BookLoan> allLoans = loanService.getAllLoans();
        
        long totalRequests = allLoans.size();
        long approvedLoans = allLoans.stream().filter(BookLoan::isApproved).count();
        long returnedLoans = allLoans.stream().filter(BookLoan::isReturned).count();
        
        // محاسبه میانگین روزهای امانت
        double averageDays = allLoans.stream()
                .filter(loan -> loan.isReturned() && loan.getReturnDate() != null)
                .mapToLong(loan -> ChronoUnit.DAYS.between(loan.getStartDate(), loan.getReturnDate()))
                .average()
                .orElse(0.0);
        
        stats.put("totalRequests", totalRequests);
        stats.put("approvedLoans", approvedLoans);
        stats.put("returnedLoans", returnedLoans);
        stats.put("averageLoanDays", String.format("%.2f", averageDays));
        stats.put("pendingLoans", loanService.getPendingLoans().size());
        
        return ResponseEntity.ok(stats);
    }
    
    @GetMapping("/employees/{id}/performance")
    public ResponseEntity<Map<String, Object>> getEmployeePerformance(@PathVariable Long id) {
        // Note: برای پیاده‌سازی دقیق‌تر نیاز به پیگیری کارمند در هر عملیات داریم
        Map<String, Object> performance = new HashMap<>();
        
        performance.put("employeeId", id);
        performance.put("booksRegistered", 0); // نیاز به پیاده‌سازی دارد
        performance.put("loansApproved", 0); // نیاز به پیاده‌سازی دارد
        performance.put("returnsProcessed", 0); // نیاز به پیاده‌سازی دارد
        performance.put("message", "Employee performance tracking not fully implemented");
        
        return ResponseEntity.ok(performance);
    }
    
    @GetMapping("/top-delayed")
    public ResponseEntity<List<Map<String, Object>>> getTopDelayedStudents() {
        List<BookLoan> allLoans = loanService.getAllLoans();
        
        List<Map<String, Object>> delayedStudents = allLoans.stream()
                .filter(loan -> loan.isOverdue() && !loan.isReturned())
                .collect(Collectors.groupingBy(BookLoan::getUser))
                .entrySet().stream()
                .map(entry -> {
                    Map<String, Object> studentData = new HashMap<>();
                    studentData.put("studentId", entry.getKey().getId());
                    studentData.put("username", entry.getKey().getUsername());
                    studentData.put("overdueLoansCount", entry.getValue().size());
                    studentData.put("totalDelayDays", entry.getValue().stream()
                            .mapToLong(BookLoan::getDaysOverdue)
                            .sum());
                    return studentData;
                })
                .sorted((a, b) -> Long.compare(
                        (Long) b.get("totalDelayDays"), 
                        (Long) a.get("totalDelayDays")))
                .limit(10)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(delayedStudents);
    }
}