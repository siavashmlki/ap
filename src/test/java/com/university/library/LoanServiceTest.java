// file: src/test/java/com/university/library/LoanServiceTest.java
package com.university.library;

import com.university.library.model.Book;
import com.university.library.model.Student;
import com.university.library.model.BookLoan;
import com.university.library.service.LoanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class LoanServiceTest {
    
    private LoanService loanService;
    private Student student;
    private Book book;
    
    @BeforeEach
    void setUp() {
        loanService = new LoanService();
        student = new Student("student1", "password");
        book = new Book("Test Book", "Test Author", 2023);
    }
    
    @Test
    void testRequestBookLoan_Success() {
        boolean result = loanService.requestBookLoan(student, book, 
            LocalDate.now(), LocalDate.now().plusDays(14));
        
        assertTrue(result);
        assertFalse(book.isAvailable());
        assertEquals(1, loanService.getTotalLoansCount());
    }
    
    @Test
    void testRequestBookLoan_BookNotAvailable() {
        book.setAvailable(false);
        boolean result = loanService.requestBookLoan(student, book, 
            LocalDate.now(), LocalDate.now().plusDays(14));
        
        assertFalse(result);
        assertEquals(0, loanService.getTotalLoansCount());
    }
    
    @Test
    void testRequestBookLoan_StudentNotActive() {
        student.setActive(false);
        boolean result = loanService.requestBookLoan(student, book, 
            LocalDate.now(), LocalDate.now().plusDays(14));
        
        assertFalse(result);
        assertEquals(0, loanService.getTotalLoansCount());
    }
    
    @Test
    void testApproveLoan() {
        loanService.requestBookLoan(student, book, LocalDate.now(), LocalDate.now().plusDays(14));
        List<BookLoan> pendingLoans = loanService.getPendingLoans();
        
        assertEquals(1, pendingLoans.size());
        
        BookLoan loan = pendingLoans.get(0);
        assertFalse(loan.isApproved());
        
        boolean approved = loanService.approveLoan(loan);
        assertTrue(approved);
        assertTrue(loan.isApproved());
    }
    
    @Test
    void testReturnBook() {
        loanService.requestBookLoan(student, book, LocalDate.now(), LocalDate.now().plusDays(14));
        List<BookLoan> loans = loanService.getLoans();
        BookLoan loan = loans.get(0);
        
        loanService.approveLoan(loan);
        boolean returned = loanService.returnBook(loan);
        
        assertTrue(returned);
        assertTrue(loan.isReturned());
        assertTrue(book.isAvailable());
        assertNotNull(loan.getReturnDate());
    }
    
    @Test
    void testGetStudentLoanHistory() {
        loanService.requestBookLoan(student, book, LocalDate.now(), LocalDate.now().plusDays(14));
        List<BookLoan> studentLoans = loanService.getStudentLoanHistory(student);
        
        assertEquals(1, studentLoans.size());
        assertEquals(student, studentLoans.get(0).getUser());
    }
    
    @Test
    void testGetActiveLoansCount() {
        assertEquals(0, loanService.getActiveLoansCount());
        
        loanService.requestBookLoan(student, book, LocalDate.now(), LocalDate.now().plusDays(14));
        List<BookLoan> loans = loanService.getLoans();
        loanService.approveLoan(loans.get(0));
        
        assertEquals(1, loanService.getActiveLoansCount());
    }
}