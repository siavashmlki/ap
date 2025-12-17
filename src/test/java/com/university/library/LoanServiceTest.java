// file: src/test/java/com/university/library/LoanServiceTest.java
package com.university.library;

import com.university.library.model.Book;
import com.university.library.model.Student;
import com.university.library.model.BookLoan;
import com.university.library.service.LoanService;
import com.university.library.exceptions.InvalidStudentStatusException;
import com.university.library.exceptions.BookNotAvailableException;
import com.university.library.exceptions.InvalidRequestStatusException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Loan Service Tests - Management Scenarios")
public class LoanServiceTest {
    
    private LoanService loanService;
    private Student activeStudent;
    private Student inactiveStudent;
    private Book availableBook;
    private Book borrowedBook;
    
    @BeforeEach
    void setUp() {
        loanService = new LoanService();
        activeStudent = new Student("activeStudent", "password");
        inactiveStudent = new Student("inactiveStudent", "password");
        inactiveStudent.setActive(false);
        
        availableBook = new Book("Available Book", "Author", 2023);
        borrowedBook = new Book("Borrowed Book", "Author", 2023);
        borrowedBook.setAvailable(false);
    }
    
    // ========== Scenario 3: Loan Management ==========
    
    @Test
    @DisplayName("3-1: Active student requests loan for available book")
    void testRequestBookLoan_ActiveStudentAvailableBook() {
        BookLoan loan = loanService.requestBookLoan(
            activeStudent, 
            availableBook, 
            LocalDate.now(), 
            LocalDate.now().plusDays(14)
        );
        
        assertNotNull(loan);
        assertEquals(activeStudent, loan.getUser());
        assertEquals(availableBook, loan.getBook());
        assertFalse(loan.isApproved());
        assertFalse(availableBook.isAvailable()); // Book should be marked as not available
    }
    
    @Test
    @DisplayName("3-2: Inactive student tries to request loan")
    void testRequestBookLoan_InactiveStudent_ThrowsException() {
        assertThrows(InvalidStudentStatusException.class, () -> {
            loanService.requestBookLoan(
                inactiveStudent, 
                availableBook, 
                LocalDate.now(), 
                LocalDate.now().plusDays(14)
            );
        });
    }
    
    @Test
    @DisplayName("3-3: Request loan for borrowed book")
    void testRequestBookLoan_BorrowedBook_ThrowsException() {
        assertThrows(BookNotAvailableException.class, () -> {
            loanService.requestBookLoan(
                activeStudent, 
                borrowedBook, 
                LocalDate.now(), 
                LocalDate.now().plusDays(14)
            );
        });
    }
    
    @Test
    @DisplayName("3-4: Approve valid loan request")
    void testApproveLoan_ValidRequest() {
        BookLoan loan = loanService.requestBookLoan(
            activeStudent, 
            availableBook, 
            LocalDate.now(), 
            LocalDate.now().plusDays(14)
        );
        
        assertFalse(loan.isApproved());
        
        loanService.approveLoan(loan);
        
        assertTrue(loan.isApproved());
    }
    
    @Test
    @DisplayName("3-5: Try to approve already approved request")
    void testApproveLoan_AlreadyApproved_ThrowsException() {
        BookLoan loan = loanService.requestBookLoan(
            activeStudent, 
            availableBook, 
            LocalDate.now(), 
            LocalDate.now().plusDays(14)
        );
        
        loanService.approveLoan(loan); // First approval
        
        assertThrows(InvalidRequestStatusException.class, () -> {
            loanService.approveLoan(loan); // Second approval should fail
        });
    }
    
    // ========== Additional Tests ==========
    
    @Test
    @DisplayName("Test return book")
    void testReturnBook() {
        BookLoan loan = loanService.requestBookLoan(
            activeStudent, 
            availableBook, 
            LocalDate.now(), 
            LocalDate.now().plusDays(14)
        );
        
        loanService.approveLoan(loan);
        boolean returned = loanService.returnBook(loan);
        
        assertTrue(returned);
        assertTrue(loan.isReturned());
        assertTrue(availableBook.isAvailable());
        assertNotNull(loan.getReturnDate());
    }
    
    @Test
    @DisplayName("Test getStudentLoanHistory")
    void testGetStudentLoanHistory() {
        loanService.requestBookLoan(activeStudent, availableBook, 
            LocalDate.now(), LocalDate.now().plusDays(14));
        
        List<BookLoan> studentLoans = loanService.getStudentLoanHistory(activeStudent);
        
        assertEquals(1, studentLoans.size());
        assertEquals(activeStudent, studentLoans.get(0).getUser());
    }
}