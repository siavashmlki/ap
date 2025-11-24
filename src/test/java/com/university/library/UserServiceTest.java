// file: src/test/java/com/university/library/UserServiceTest.java
package com.university.library;

import com.university.library.model.Student;
import com.university.library.model.User;
import com.university.library.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {
    
    private UserService userService;
    
    @BeforeEach
    void setUp() {
        userService = new UserService();
    }
    
    @Test
    void testRegisterStudent_Success() {
        boolean result = userService.registerStudent("student1", "password123");
        assertTrue(result);
        assertEquals(1, userService.getRegisteredStudentsCount());
    }
    
    @Test
    void testRegisterStudent_DuplicateUsername() {
        userService.registerStudent("student1", "password123");
        boolean result = userService.registerStudent("student1", "differentpassword");
        assertFalse(result);
        assertEquals(1, userService.getRegisteredStudentsCount());
    }
    
    @Test
    void testLogin_Success() {
        userService.registerStudent("student1", "password123");
        User user = userService.login("student1", "password123");
        assertNotNull(user);
        assertTrue(user instanceof Student);
        assertEquals("student1", user.getUsername());
    }
    
    @Test
    void testLogin_WrongPassword() {
        userService.registerStudent("student1", "password123");
        User user = userService.login("student1", "wrongpassword");
        assertNull(user);
    }
    
    @Test
    void testLogin_UserNotFound() {
        User user = userService.login("nonexistent", "password");
        assertNull(user);
    }
    
    @Test
    void testGetRegisteredStudentsCount() {
        assertEquals(0, userService.getRegisteredStudentsCount());
        userService.registerStudent("student1", "pass1");
        assertEquals(1, userService.getRegisteredStudentsCount());
        userService.registerStudent("student2", "pass2");
        assertEquals(2, userService.getRegisteredStudentsCount());
    }
}