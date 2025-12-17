// file: src/main/java/com/university/library/model/StudentReport.java
package com.university.library.model;

public class StudentReport {
    private User student;
    private int totalLoans;
    private int notReturnedCount;
    private int overdueLoansCount;
    
    public StudentReport(User student, int totalLoans, int notReturnedCount, int overdueLoansCount) {
        this.student = student;
        this.totalLoans = totalLoans;
        this.notReturnedCount = notReturnedCount;
        this.overdueLoansCount = overdueLoansCount;
    }
    
    // Getters
    public User getStudent() { return student; }
    public int getTotalLoans() { return totalLoans; }
    public int getNotReturnedCount() { return notReturnedCount; }
    public int getOverdueLoansCount() { return overdueLoansCount; }
    
    @Override
    public String toString() {
        return String.format(
            "StudentReport{student=%s, totalLoans=%d, notReturned=%d, overdue=%d}",
            student.getUsername(), totalLoans, notReturnedCount, overdueLoansCount
        );
    }
}