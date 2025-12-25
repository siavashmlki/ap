package com.university.library.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "students")
@DiscriminatorValue("STUDENT")
public class Student extends User {
    
    @Column(name = "student_id", unique = true)
    private String studentId;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BookLoan> loans = new ArrayList<>();
    
    public Student() {}
    
    public Student(String username, String password) {
        super(username, password);
    }
    
    public Student(String username, String password, String studentId) {
        super(username, password);
        this.studentId = studentId;
    }
    
    // Getters and Setters
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    
    public List<BookLoan> getLoans() { return loans; }
    public void setLoans(List<BookLoan> loans) { this.loans = loans; }
    
    @Override
    public String getUserType() {
        return "STUDENT";
    }
}