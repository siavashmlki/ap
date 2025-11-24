// file: src/main/java/com/university/library/model/Student.java
package com.university.library.model;

public class Student extends User {
    public Student(String username, String password) {
        super(username, password);
    }

    @Override
    public String getUserType() {
        return "STUDENT";
    }
}