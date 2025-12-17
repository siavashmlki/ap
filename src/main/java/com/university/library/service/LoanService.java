package com.university.library.service;

import com.university.library.model.Book;
import com.university.library.model.User;
import com.university.library.model.BookLoan;
import com.university.library.model.StudentReport;
import com.university.library.model.LibraryStats;
import com.university.library.exceptions.InvalidStudentStatusException;
import com.university.library.exceptions.BookNotAvailableException;
import com.university.library.exceptions.InvalidRequestStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LoanService {
    private List<BookLoan> loans;
    
    public LoanService() {
        this.loans = new ArrayList<>();
    }
    
    public BookLoan requestBookLoan(User user, Book book, LocalDate startDate, LocalDate endDate) {
        // Scenario 3-2: Inactive student
        if (!user.isActive()) {
            throw new InvalidStudentStatusException("Student is not active");
        }
        
        // Scenario 3-3: Book not available
        if (!book.isAvailable()) {
            throw new BookNotAvailableException("Book is not available for loan");
        }
        
        BookLoan loan = new BookLoan(user, book, startDate, endDate);
        loans.add(loan);
        book.setAvailable(false);
        return loan;
    }
    
    public void approveLoan(BookLoan loan) {
        // Scenario 3-5: Already approved request
        if (loan.isApproved()) {
            throw new InvalidRequestStatusException("Loan request is already approved");
        }
        
        loan.setApproved(true);
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
    
    // ========== NEW METHODS FOR SCENARIO 4 (REPORTING) ==========
    
    public StudentReport generateStudentReport(User student) {
        List<BookLoan> studentLoans = getStudentLoanHistory(student);
        int totalLoans = studentLoans.size();
        int notReturnedCount = 0;
        int overdueLoansCount = 0;
        
        for (BookLoan loan : studentLoans) {
            if (!loan.isReturned()) {
                notReturnedCount++;
            }
            if (loan.isOverdue()) {
                overdueLoansCount++;
            }
        }
        
        return new StudentReport(student, totalLoans, notReturnedCount, overdueLoansCount);
    }
    
    public LibraryStats generateLibraryStats() {
        int totalLoans = getTotalLoansCount();
        int approvedLoans = 0;
        long totalLoanDays = 0;
        int completedLoans = 0;
        
        for (BookLoan loan : loans) {
            if (loan.isApproved()) {
                approvedLoans++;
            }
            if (loan.isReturned() && loan.getReturnDate() != null) {
                long loanDays = loan.getReturnDate().toEpochDay() - loan.getStartDate().toEpochDay();
                totalLoanDays += loanDays;
                completedLoans++;
            }
        }
        
        double averageLoanDays = completedLoans > 0 ? (double) totalLoanDays / completedLoans : 0.0;
        
        return new LibraryStats(totalLoans, approvedLoans, averageLoanDays);
    }
}