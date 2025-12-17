// file: src/test/java/com/university/library/BookServiceTest.java
package com.university.library;

import com.university.library.model.Book;
import com.university.library.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Book Service Tests - Search Scenarios")
public class BookServiceTest {
    
    private BookService bookService;
    
    @BeforeEach
    void setUp() {
        bookService = new BookService();
        // Add test books
        bookService.addBook("Java Programming", "John Doe", 2020);
        bookService.addBook("Python Basics", "Jane Smith", 2019);
        bookService.addBook("Advanced Java", "John Doe", 2022);
        bookService.addBook("Data Structures", "Bob Johnson", 2021);
        bookService.addBook("Python for Data Science", "Jane Smith", 2020);
    }
    
    // ========== Scenario 2: Book Search Service ==========
    
    @Test
    @DisplayName("2-1: Search only by title")
    void testSearchBooks_ByTitleOnly() {
        List<Book> results = bookService.searchBooks("Java", null, null);
        
        assertEquals(2, results.size());
        assertTrue(results.stream().allMatch(book -> 
            book.getTitle().contains("Java")));
    }
    
    @Test
    @DisplayName("2-2: Search by author and publication year combination")
    void testSearchBooks_ByAuthorAndYear() {
        List<Book> results = bookService.searchBooks(null, "John Doe", 2020);
        
        assertEquals(1, results.size());
        Book book = results.get(0);
        assertEquals("Java Programming", book.getTitle());
        assertEquals("John Doe", book.getAuthor());
        assertEquals(2020, book.getPublicationYear());
    }
    
    @Test
    @DisplayName("2-3: Search without any criteria (all parameters null)")
    void testSearchBooks_AllParametersNull() {
        List<Book> results = bookService.searchBooks(null, null, null);
        
        assertEquals(5, results.size()); // All books should be returned
    }
    
    @Test
    @DisplayName("2-4: Search with no matching books")
    void testSearchBooks_NoMatchingBooks() {
        List<Book> results = bookService.searchBooks("NonExistentBook", null, null);
        
        assertTrue(results.isEmpty());
    }
    
    // ========== Additional Tests ==========
    
    @Test
    @DisplayName("Test searchBooksByTitle")
    void testSearchBooksByTitle() {
        List<Book> results = bookService.searchBooksByTitle("Python");
        assertEquals(2, results.size());
        
        results = bookService.searchBooksByTitle("Java");
        assertEquals(2, results.size());
    }
    
    @Test
    @DisplayName("Test addBook")
    void testAddBook() {
        int initialCount = bookService.getTotalBooksCount();
        bookService.addBook("New Book", "New Author", 2023);
        assertEquals(initialCount + 1, bookService.getTotalBooksCount());
    }
    
    @Test
    @DisplayName("Test getTotalBooksCount")
    void testGetTotalBooksCount() {
        assertEquals(5, bookService.getTotalBooksCount());
    }
}