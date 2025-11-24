// file: src/main/java/com/university/library/service/UserService.java
package com.university.library.service;

import com.university.library.model.Student;
import com.university.library.model.User;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    private List<User> users;
    
    public UserService() {
        this.users = new ArrayList<>();
    }
    
    public boolean registerStudent(String username, String password) {
        // Check if username already exists
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return false;
            }
        }
        
        Student student = new Student(username, password);
        users.add(student);
        return true;
    }
    
    public User login(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && 
                user.getPassword().equals(password) && 
                user.isActive()) {
                return user;
            }
        }
        return null;
    }
    
    public int getRegisteredStudentsCount() {
        int count = 0;
        for (User user : users) {
            if (user instanceof Student) {
                count++;
            }
        }
        return count;
    }
    
    public List<User> getUsers() {
        return new ArrayList<>(users);
    }
}