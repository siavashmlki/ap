// file: src/main/java/com/university/library/model/Admin.java
package com.university.library.model;

public class Admin extends User {
    public Admin(String username, String password) {
        super(username, password);
    }

    @Override
    public String getUserType() {
        return "ADMIN";
    }
}