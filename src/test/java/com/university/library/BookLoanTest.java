// file: src/test/java/com/university/library/BookLoanTest.java
package com.university.library;

import com.university.library.model.Book;
import com.university.library.model.BookLoan;
import com.university.library.model.Student;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class BookLoanTest {
    
    @Test
    void testBookLoanCreation() {
        Student student = new Student("student1", "password");
        Book book = new Book("Test Book", "Author", 2023);
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(14);
        
        BookLoan loan = new BookLoan(student, book, startDate, endDate);
        
        assertEquals(student, loan.getUser());
        assertEquals(book, loan.getBook());
        assertEquals(startDate, loan.getStartDate());
        assertEquals(endDate, loan.getEndDate());
        assertFalse(loan.isApproved());
        assertFalse(loan.isReturned());
        assertNull(loan.getReturnDate());
    }
    
    @Test
    void testBookLoanApproval() {
        Student student = new Student("student1", "password");
        Book book = new Book("Test Book", "Author", 2023);
        BookLoan loan = new BookLoan(student, book, LocalDate.now(), LocalDate.now().plusDays(14));
        
        assertFalse(loan.isApproved());
        loan.setApproved(true);
        assertTrue(loan.isApproved());
    }
    
    @Test
    void testBookLoanReturn() {
        Student student = new Student("student1", "password");
        Book book = new Book("Test Book", "Author", 2023);
        BookLoan loan = new BookLoan(student, book, LocalDate.now(), LocalDate.now().plusDays(14));
        LocalDate returnDate = LocalDate.now();
        
        assertFalse(loan.isReturned());
        loan.setReturned(true);
        loan.setReturnDate(returnDate);
        
        assertTrue(loan.isReturned());
        assertEquals(returnDate, loan.getReturnDate());
    }
    
    @Test
    void testBookLoanNotOverdue() {
        Student student = new Student("student1", "password");
        Book book = new Book("Test Book", "Author", 2023);
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(14);
        
        BookLoan loan = new BookLoan(student, book, startDate, endDate);
        
        assertFalse(loan.isOverdue());
    }
}