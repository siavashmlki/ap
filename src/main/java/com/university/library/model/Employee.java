// file: src/main/java/com/university/library/model/Employee.java
package com.university.library.model;

public class Employee extends User {
    public Employee(String username, String password) {
        super(username, password);
    }

    @Override
    public String getUserType() {
        return "EMPLOYEE";
    }
}