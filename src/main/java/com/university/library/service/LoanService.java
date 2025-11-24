// file: src/main/java/com/university/library/service/LoanService.java
package com.university.library.service;

import com.university.library.model.Book;
import com.university.library.model.User;
import com.university.library.model.BookLoan;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LoanService {
    private List<BookLoan> loans;
    
    public LoanService() {
        this.loans = new ArrayList<>();
    }
    
    public boolean requestBookLoan(User user, Book book, LocalDate startDate, LocalDate endDate) {
        if (!book.isAvailable() || !user.isActive()) {
            return false;
        }
        
        BookLoan loan = new BookLoan(user, book, startDate, endDate);
        loans.add(loan);
        book.setAvailable(false);
        return true;
    }
    
    public List<BookLoan> getPendingLoans() {
        List<BookLoan> pending = new ArrayList<>();
        for (BookLoan loan : loans) {
            if (!loan.isApproved() && !loan.isReturned()) {
                pending.add(loan);
            }
        }
        return pending;
    }
    
    public boolean approveLoan(BookLoan loan) {
        if (loan != null && !loan.isApproved()) {
            loan.setApproved(true);
            return true;
        }
        return false;
    }
    
    public boolean returnBook(BookLoan loan) {
        if (loan != null && !loan.isReturned()) {
            loan.setReturned(true);
            loan.setReturnDate(LocalDate.now());
            loan.getBook().setAvailable(true);
            return true;
        }
        return false;
    }
    
    public List<BookLoan> getStudentLoanHistory(User student) {
        List<BookLoan> studentLoans = new ArrayList<>();
        for (BookLoan loan : loans) {
            if (loan.getUser().equals(student)) {
                studentLoans.add(loan);
            }
        }
        return studentLoans;
    }
    
    public int getTotalLoansCount() {
        return loans.size();
    }
    
    public int getActiveLoansCount() {
        int count = 0;
        for (BookLoan loan : loans) {
            if (loan.isApproved() && !loan.isReturned()) {
                count++;
            }
        }
        return count;
    }
    
    public List<BookLoan> getLoans() {
        return new ArrayList<>(loans);
    }
}