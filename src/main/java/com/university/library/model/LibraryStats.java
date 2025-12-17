// file: src/main/java/com/university/library/model/LibraryStats.java
package com.university.library.model;

public class LibraryStats {
    private int totalLoans;
    private int approvedLoans;
    private double averageLoanDays;
    
    public LibraryStats(int totalLoans, int approvedLoans, double averageLoanDays) {
        this.totalLoans = totalLoans;
        this.approvedLoans = approvedLoans;
        this.averageLoanDays = averageLoanDays;
    }
    
    // Getters
    public int getTotalLoans() { return totalLoans; }
    public int getApprovedLoans() { return approvedLoans; }
    public double getAverageLoanDays() { return averageLoanDays; }
    
    @Override
    public String toString() {
        return String.format(
            "LibraryStats{totalLoans=%d, approvedLoans=%d, avgLoanDays=%.2f}",
            totalLoans, approvedLoans, averageLoanDays
        );
    }
}