// file: src/test/java/com/university/library/ReportServiceTest.java
package com.university.library;

import com.university.library.model.*;
import com.university.library.service.LoanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Report Service Tests - Reporting Scenarios")
public class ReportServiceTest {
    
    private LoanService loanService;
    private Student student1;
    private Student student2;
    private Book book1;
    private Book book2;
    
    @BeforeEach
    void setUp() {
        loanService = new LoanService();
        student1 = new Student("student1", "pass");
        student2 = new Student("student2", "pass");
        book1 = new Book("Book 1", "Author 1", 2023);
        book2 = new Book("Book 2", "Author 2", 2023);
    }
    
    // ========== Scenario 4: Reporting Service ==========
    
    @Test
    @DisplayName("4-1: Generate report for a student")
    void testGenerateStudentReport() {
        // Student 1 has 2 loans: one returned, one not returned and overdue
        loanService.requestBookLoan(student1, book1, 
            LocalDate.now().minusDays(20), 
            LocalDate.now().minusDays(10)); // Overdue loan
        
        loanService.requestBookLoan(student1, book2, 
            LocalDate.now().minusDays(30), 
            LocalDate.now().minusDays(20));
        
        // Approve both loans
        for (BookLoan loan : loanService.getLoans()) {
            loanService.approveLoan(loan);
        }
        
        // Return the second loan
        loanService.getLoans().get(1).setReturned(true);
        loanService.getLoans().get(1).setReturnDate(LocalDate.now().minusDays(15));
        
        StudentReport report = loanService.generateStudentReport(student1);
        
        assertEquals(student1, report.getStudent());
        assertEquals(2, report.getTotalLoans());
        assertEquals(1, report.getNotReturnedCount()); // One loan not returned
        assertEquals(1, report.getOverdueLoansCount()); // One loan overdue
    }
    
    @Test
    @DisplayName("4-2: Calculate overall library statistics")
    void testGenerateLibraryStats() {
        // Create multiple loans with different scenarios
        loanService.requestBookLoan(student1, book1, 
            LocalDate.now().minusDays(30), 
            LocalDate.now().minusDays(10));
        
        loanService.requestBookLoan(student2, book2, 
            LocalDate.now().minusDays(20), 
            LocalDate.now().plusDays(10));
        
        // Approve and return some loans
        for (BookLoan loan : loanService.getLoans()) {
            loanService.approveLoan(loan);
        }
        
        // Return first loan (20 days duration)
        loanService.getLoans().get(0).setReturned(true);
        loanService.getLoans().get(0).setReturnDate(LocalDate.now().minusDays(10));
        
        LibraryStats stats = loanService.generateLibraryStats();
        
        assertEquals(2, stats.getTotalLoans());
        assertEquals(2, stats.getApprovedLoans());
        // Average loan days: only one loan returned with 20 days
        assertEquals(20.0, stats.getAverageLoanDays(), 0.01);
    }
    
    @Test
    @DisplayName("Test report for student with no loans")
    void testGenerateStudentReport_NoLoans() {
        StudentReport report = loanService.generateStudentReport(student1);
        
        assertEquals(student1, report.getStudent());
        assertEquals(0, report.getTotalLoans());
        assertEquals(0, report.getNotReturnedCount());
        assertEquals(0, report.getOverdueLoansCount());
    }
    
    @Test
    @DisplayName("Test library stats with no returned loans")
    void testGenerateLibraryStats_NoReturns() {
        loanService.requestBookLoan(student1, book1, 
            LocalDate.now(), 
            LocalDate.now().plusDays(14));
        loanService.approveLoan(loanService.getLoans().get(0));
        
        LibraryStats stats = loanService.generateLibraryStats();
        
        assertEquals(1, stats.getTotalLoans());
        assertEquals(1, stats.getApprovedLoans());
        assertEquals(0.0, stats.getAverageLoanDays(), 0.01); // No returned loans
    }
}