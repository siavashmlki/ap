// file: src/main/java/com/university/library/model/BookLoan.java
package com.university.library.model;

import java.time.LocalDate;

public class BookLoan {
    private User user;
    private Book book;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean approved;
    private boolean returned;
    private LocalDate returnDate;
    
    public BookLoan(User user, Book book, LocalDate startDate, LocalDate endDate) {
        this.user = user;
        this.book = book;
        this.startDate = startDate;
        this.endDate = endDate;
        this.approved = false;
        this.returned = false;
    }
    
    // Getters and setters
    public User getUser() { return user; }
    public Book getBook() { return book; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public boolean isApproved() { return approved; }
    public void setApproved(boolean approved) { this.approved = approved; }
    public boolean isReturned() { return returned; }
    public void setReturned(boolean returned) { this.returned = returned; }
    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }
    
    public boolean isOverdue() {
        return !returned && LocalDate.now().isAfter(endDate);
    }
    
    public long getDaysOverdue() {
        if (!isOverdue()) return 0;
        return LocalDate.now().toEpochDay() - endDate.toEpochDay();
    }
}