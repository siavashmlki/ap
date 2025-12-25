package com.university.library.service;

import com.university.library.entity.Book;
import com.university.library.entity.BookLoan;
import com.university.library.entity.User;
import com.university.library.exceptions.BookNotAvailableException;
import com.university.library.exceptions.InvalidRequestStatusException;
import com.university.library.exceptions.InvalidStudentStatusException;
import com.university.library.repository.BookLoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LoanService {
    
    @Autowired
    private BookLoanRepository bookLoanRepository;
    
    public BookLoan requestBookLoan(User user, Book book, LocalDate startDate, LocalDate endDate) {
        if (!user.isActive()) {
            throw new InvalidStudentStatusException("Student is not active");
        }
        
        if (!book.isAvailable()) {
            throw new BookNotAvailableException("Book is not available for loan");
        }
        
        BookLoan loan = new BookLoan(user, book, startDate, endDate);
        book.setAvailable(false);
        
        return bookLoanRepository.save(loan);
    }
    
    public void approveLoan(Long loanId) {
        Optional<BookLoan> optionalLoan = bookLoanRepository.findById(loanId);
        if (optionalLoan.isPresent()) {
            BookLoan loan = optionalLoan.get();
            if (loan.isApproved()) {
                throw new InvalidRequestStatusException("Loan request is already approved");
            }
            loan.setApproved(true);
            bookLoanRepository.save(loan);
        }
    }
    
    public void returnBook(Long loanId) {
        Optional<BookLoan> optionalLoan = bookLoanRepository.findById(loanId);
        if (optionalLoan.isPresent()) {
            BookLoan loan = optionalLoan.get();
            if (!loan.isReturned()) {
                loan.setReturned(true);
                loan.setReturnDate(LocalDate.now());
                loan.getBook().setAvailable(true);
                bookLoanRepository.save(loan);
            }
        }
    }
    
    public List<BookLoan> getPendingLoans() {
        return bookLoanRepository.findByApprovedFalseAndReturnedFalse();
    }
    
    public List<BookLoan> getStudentLoanHistory(User student) {
        return bookLoanRepository.findByUser(student);
    }
    
    public long getTotalLoansCount() {
        return bookLoanRepository.count();
    }
    
    public long getActiveLoansCount() {
        return bookLoanRepository.countByApprovedTrue();
    }
    
    public Optional<BookLoan> findLoanById(Long id) {
        return bookLoanRepository.findById(id);
    }
    
    public List<BookLoan> getAllLoans() {
        return bookLoanRepository.findAll();
    }
}