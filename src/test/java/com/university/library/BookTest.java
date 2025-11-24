// file: src/test/java/com/university/library/BookTest.java
package com.university.library;

import com.university.library.model.Book;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BookTest {
    
    @Test
    void testBookCreation() {
        Book book = new Book("Test Book", "Test Author", 2023);
        
        assertEquals("Test Book", book.getTitle());
        assertEquals("Test Author", book.getAuthor());
        assertEquals(2023, book.getPublicationYear());
        assertTrue(book.isAvailable());
    }
    
    @Test
    void testBookAvailability() {
        Book book = new Book("Test Book", "Test Author", 2023);
        assertTrue(book.isAvailable());
        
        book.setAvailable(false);
        assertFalse(book.isAvailable());
        
        book.setAvailable(true);
        assertTrue(book.isAvailable());
    }
    
    @Test
    void testBookToString() {
        Book book = new Book("Test Book", "Test Author", 2023);
        String toString = book.toString();
        
        assertTrue(toString.contains("Test Book"));
        assertTrue(toString.contains("Test Author"));
        assertTrue(toString.contains("2023"));
    }
}