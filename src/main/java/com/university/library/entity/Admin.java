// file: src/main/java/com/university/library/entity/Admin.java
package com.university.library.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "admins")
@DiscriminatorValue("ADMIN")
public class Admin extends User {
    
    @Column(name = "admin_id", unique = true)
    private String adminId;
    
    public Admin() {}
    
    public Admin(String username, String password) {
        super(username, password);
    }
    
    public Admin(String username, String password, String adminId) {
        super(username, password);
        this.adminId = adminId;
    }
    
    // Getters and Setters
    public String getAdminId() { return adminId; }
    public void setAdminId(String adminId) { this.adminId = adminId; }
    
    @Override
    public String getUserType() {
        return "ADMIN";
    }
}