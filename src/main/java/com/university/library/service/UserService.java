package com.university.library.service;

import com.university.library.entity.Student;
import com.university.library.entity.User;
import com.university.library.repository.StudentRepository;
import com.university.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private StudentRepository studentRepository;
    
    public boolean registerStudent(String username, String password) {
        if (userRepository.existsByUsername(username)) {
            return false;
        }
        
        Student student = new Student(username, password);
        userRepository.save(student);
        return true;
    }
    
    public Optional<User> login(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password)
                .filter(User::isActive);
    }
    
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    public long getRegisteredStudentsCount() {
        return studentRepository.count();
    }
    
    public User saveUser(User user) {
        return userRepository.save(user);
    }
    
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
    
    // ========== متدهای جدید برای تکمیل فاز چهارم ==========
    
    /**
     * پیدا کردن دانشجو بر اساس آیدی
     */
    public Optional<Student> findStudentById(Long id) {
        return userRepository.findById(id)
                .filter(user -> user instanceof Student)
                .map(user -> (Student) user);
    }
    
    /**
     * به‌روزرسانی وضعیت فعال/غیرفعال دانشجو
     */
    public boolean updateStudentStatus(Long studentId, boolean active) {
        return findStudentById(studentId)
                .map(student -> {
                    student.setActive(active);
                    userRepository.save(student);
                    return true;
                })
                .orElse(false);
    }
    
    /**
     * تعداد کل کاربران
     */
    public long getTotalUsersCount() {
        return userRepository.count();
    }
    
    /**
     * تعداد دانشجویان فعال
     */
    public long getActiveStudentsCount() {
        return userRepository.findAll().stream()
                .filter(user -> user instanceof Student && user.isActive())
                .count();
    }
    
    /**
     * لیست تمام دانشجویان
     */
    public List<Student> getAllStudents() {
        return userRepository.findAll().stream()
                .filter(user -> user instanceof Student)
                .map(user -> (Student) user)
                .collect(Collectors.toList());
    }
    
    /**
     * تغییر رمز عبور
     */
    public boolean changePassword(Long userId, String oldPassword, String newPassword) {
        return userRepository.findById(userId)
                .filter(user -> user.getPassword().equals(oldPassword))
                .map(user -> {
                    user.setPassword(newPassword);
                    userRepository.save(user);
                    return true;
                })
                .orElse(false);
    }
    
    /**
     * بررسی وجود کاربر با آیدی مشخص
     */
    public boolean userExists(Long userId) {
        return userRepository.existsById(userId);
    }
    
    /**
     * تعداد کاربران غیرفعال
     */
    public long getInactiveUsersCount() {
        return userRepository.findAll().stream()
                .filter(user -> !user.isActive())
                .count();
    }
    
    /**
     * پیدا کردن کاربر بر اساس نوع (Student, Employee, Admin)
     */
    public Optional<User> findUserByType(Long id, String userType) {
        return userRepository.findById(id)
                .filter(user -> user.getUserType().equalsIgnoreCase(userType));
    }
    
    /**
     * فعال کردن کاربر
     */
    public boolean activateUser(Long userId) {
        return userRepository.findById(userId)
                .map(user -> {
                    user.setActive(true);
                    userRepository.save(user);
                    return true;
                })
                .orElse(false);
    }
    
    /**
     * غیرفعال کردن کاربر
     */
    public boolean deactivateUser(Long userId) {
        return userRepository.findById(userId)
                .map(user -> {
                    user.setActive(false);
                    userRepository.save(user);
                    return true;
                })
                .orElse(false);
    }
}