// file: src/main/java/com/university/library/LibraryManagementSystem.java
package com.university.library;

import com.university.library.model.*;
import com.university.library.service.UserService;
import com.university.library.service.BookService;
import com.university.library.service.LoanService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class LibraryManagementSystem {
    private UserService userService;
    private BookService bookService;
    private LoanService loanService;
    private Scanner scanner;
    private User currentUser;
    
    public LibraryManagementSystem() {
        this.userService = new UserService();
        this.bookService = new BookService();
        this.loanService = new LoanService();
        this.scanner = new Scanner(System.in);
        initializeSampleData();
    }
    
    private void initializeSampleData() {
        // Add sample books
        bookService.addBook("Java Programming", "John Doe", 2020);
        bookService.addBook("Python Basics", "Jane Smith", 2019);
        bookService.addBook("Advanced Java", "John Doe", 2022);
        bookService.addBook("Data Structures", "Bob Johnson", 2021);
        
        // Register sample students
        userService.registerStudent("ali", "123");
        userService.registerStudent("maryam", "456");
    }
    
    public void start() {
        while (true) {
            showMainMenu();
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    studentMenu();
                    break;
                case "2":
                    employeeMenu();
                    break;
                case "3":
                    adminMenu();
                    break;
                case "4":
                    guestMenu();
                    break;
                case "5":
                    System.out.println("Exiting system...");
                    return;
                default:
                    System.out.println("Invalid input! Please try again.");
            }
        }
    }
    
    private void showMainMenu() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("University Library Management System");
        System.out.println("=".repeat(40));
        System.out.println("1. Login as Student");
        System.out.println("2. Login as Employee");
        System.out.println("3. Login as Admin");
        System.out.println("4. Continue as Guest");
        System.out.println("5. Exit");
        System.out.println("=".repeat(40));
        System.out.print("Choose an option: ");
    }
    
    private void studentMenu() {
        System.out.println("\n--- Student Menu ---");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Search Book");
        System.out.println("4. Request Book Loan");
        System.out.println("5. Return to Main Menu");
        System.out.print("Choose an option: ");
        
        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                registerStudent();
                break;
            case "2":
                loginStudent();
                break;
            case "3":
                searchBooks();
                break;
            case "4":
                requestBookLoan();
                break;
            case "5":
                return;
            default:
                System.out.println("Invalid option!");
        }
    }
    
    private void registerStudent() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        
        boolean success = userService.registerStudent(username, password);
        if (success) {
            System.out.println("Registration successful!");
        } else {
            System.out.println("Registration failed! Username already exists.");
        }
    }
    
    private void loginStudent() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        
        currentUser = userService.login(username, password);
        if (currentUser != null && currentUser instanceof Student) {
            System.out.println("Login successful! Welcome " + username);
            loggedInStudentMenu();
        } else {
            System.out.println("Login failed! Invalid credentials or user not active.");
        }
    }
    
    private void loggedInStudentMenu() {
        // Implementation for logged in student
        System.out.println("Student logged in functionality will be implemented in next phases");
    }
    
    private void searchBooks() {
        System.out.print("Enter title (or press enter to skip): ");
        String title = scanner.nextLine();
        System.out.print("Enter author (or press enter to skip): ");
        String author = scanner.nextLine();
        System.out.print("Enter publication year (or press enter to skip): ");
        String yearInput = scanner.nextLine();
        
        Integer year = null;
        if (!yearInput.isEmpty()) {
            try {
                year = Integer.parseInt(yearInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid year format!");
                return;
            }
        }
        
        List<Book> results = bookService.searchBooks(
            title.isEmpty() ? null : title,
            author.isEmpty() ? null : author,
            year
        );
        
        System.out.println("\nSearch Results:");
        if (results.isEmpty()) {
            System.out.println("No books found.");
        } else {
            for (Book book : results) {
                System.out.printf("- %s by %s (%d) - %s%n",
                    book.getTitle(),
                    book.getAuthor(),
                    book.getPublicationYear(),
                    book.isAvailable() ? "Available" : "Borrowed");
            }
        }
    }
    
    private void requestBookLoan() {
        System.out.println("Book loan request functionality will be implemented in next phases");
    }
    
    private void guestMenu() {
        System.out.println("\n--- Guest Menu ---");
        System.out.println("1. View number of registered students");
        System.out.println("2. Search book by name");
        System.out.println("3. View general statistics");
        System.out.println("4. Return to Main Menu");
        System.out.print("Choose an option: ");
        
        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                showStudentCount();
                break;
            case "2":
                searchBookByName();
                break;
            case "3":
                showStatistics();
                break;
            case "4":
                return;
            default:
                System.out.println("Invalid option!");
        }
    }
    
    private void showStudentCount() {
        int count = userService.getRegisteredStudentsCount();
        System.out.println("Number of registered students: " + count);
    }
    
    private void searchBookByName() {
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        
        List<Book> results = bookService.searchBooksByTitle(title);
        System.out.println("\nSearch Results:");
        if (results.isEmpty()) {
            System.out.println("No books found.");
        } else {
            for (Book book : results) {
                System.out.printf("- %s by %s (%d)%n",
                    book.getTitle(),
                    book.getAuthor(),
                    book.getPublicationYear());
            }
        }
    }
    
    private void showStatistics() {
        System.out.println("\n--- Library Statistics ---");
        System.out.println("Total Students: " + userService.getRegisteredStudentsCount());
        System.out.println("Total Books: " + bookService.getTotalBooksCount());
        System.out.println("Total Loans: " + loanService.getTotalLoansCount());
        System.out.println("Active Loans: " + loanService.getActiveLoansCount());
    }
    
    private void employeeMenu() {
        System.out.println("Employee menu functionality will be implemented in next phases");
    }
    
    private void adminMenu() {
        System.out.println("Admin menu functionality will be implemented in next phases");
    }
    
    public static void main(String[] args) {
        LibraryManagementSystem system = new LibraryManagementSystem();
        system.start();
    }
}