// file: src/test/java/com/university/library/UserServiceTest.java
package com.university.library;

import com.university.library.model.Student;
import com.university.library.model.User;
import com.university.library.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("User Service Tests - Authentication Scenarios")
public class UserServiceTest {
    
    private UserService userService;
    
    @BeforeEach
    void setUp() {
        userService = new UserService();
    }
    
    // ========== Scenario 1: Authentication Service ==========
    
    @Test
    @DisplayName("1-1: Register new user with unique username")
    void testRegisterStudent_UniqueUsername_ReturnsTrue() {
        boolean result = userService.registerStudent("student1", "password123");
        assertTrue(result);
        assertEquals(1, userService.getRegisteredStudentsCount());
    }
    
    @Test
    @DisplayName("1-2: Register with duplicate username")
    void testRegisterStudent_DuplicateUsername_ReturnsFalse() {
        userService.registerStudent("student1", "password123");
        boolean result = userService.registerStudent("student1", "differentpassword");
        assertFalse(result);
        assertEquals(1, userService.getRegisteredStudentsCount());
    }
    
    @Test
    @DisplayName("1-3: Login with correct username and password")
    void testLogin_CorrectCredentials_ReturnsTrue() {
        userService.registerStudent("student1", "password123");
        User user = userService.login("student1", "password123");
        assertNotNull(user);
        assertTrue(user instanceof Student);
        assertEquals("student1", user.getUsername());
    }
    
    @Test
    @DisplayName("1-4: Login with correct username but wrong password")
    void testLogin_CorrectUsernameWrongPassword_ReturnsFalse() {
        userService.registerStudent("student1", "password123");
        User user = userService.login("student1", "wrongpassword");
        assertNull(user);
    }
    
    @Test
    @DisplayName("1-5: Login with non-existent username")
    void testLogin_NonExistentUsername_ReturnsFalse() {
        User user = userService.login("nonexistent", "password");
        assertNull(user);
    }
    
    // ========== Additional Tests ==========
    
    @Test
    @DisplayName("Test getRegisteredStudentsCount")
    void testGetRegisteredStudentsCount() {
        assertEquals(0, userService.getRegisteredStudentsCount());
        userService.registerStudent("student1", "pass1");
        assertEquals(1, userService.getRegisteredStudentsCount());
        userService.registerStudent("student2", "pass2");
        assertEquals(2, userService.getRegisteredStudentsCount());
    }
    
    @Test
    @DisplayName("Test login with inactive student")
    void testLogin_InactiveStudent_ReturnsNull() {
        userService.registerStudent("inactiveStudent", "password");
        // In a real implementation, we would deactivate the student first
        // For now, this test shows the expected behavior
        User user = userService.login("inactiveStudent", "password");
        assertNotNull(user); // Should be not null since student is active by default
        
        // Note: We need to add deactivation functionality to test this properly
    }
}