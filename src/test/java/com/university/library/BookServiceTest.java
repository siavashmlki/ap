// file: src/test/java/com/university/library/BookServiceTest.java
package com.university.library;

import com.university.library.model.Book;
import com.university.library.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class BookServiceTest {
    
    private BookService bookService;
    
    @BeforeEach
    void setUp() {
        bookService = new BookService();
        // Add some test books
        bookService.addBook("Java Programming", "John Doe", 2020);
        bookService.addBook("Python Basics", "Jane Smith", 2019);
        bookService.addBook("Advanced Java", "John Doe", 2022);
    }
    
    @Test
    void testAddBook() {
        int initialCount = bookService.getTotalBooksCount();
        bookService.addBook("New Book", "New Author", 2023);
        assertEquals(initialCount + 1, bookService.getTotalBooksCount());
    }
    
    @Test
    void testSearchBooksByTitle() {
        List<Book> results = bookService.searchBooksByTitle("Java");
        assertEquals(2, results.size());
        
        results = bookService.searchBooksByTitle("Python");
        assertEquals(1, results.size());
        assertEquals("Python Basics", results.get(0).getTitle());
    }
    
    @Test
    void testSearchBooks_ByTitleAndAuthor() {
        List<Book> results = bookService.searchBooks("Java", "John Doe", null);
        assertEquals(2, results.size());
        
        results = bookService.searchBooks("Java", "Jane Smith", null);
        assertEquals(0, results.size());
    }
    
    @Test
    void testSearchBooks_ByPublicationYear() {
        List<Book> results = bookService.searchBooks(null, null, 2020);
        assertEquals(1, results.size());
        assertEquals("Java Programming", results.get(0).getTitle());
    }
    
    @Test
    void testGetTotalBooksCount() {
        assertEquals(3, bookService.getTotalBooksCount());
    }
}