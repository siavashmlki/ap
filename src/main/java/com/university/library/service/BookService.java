package com.university.library.service;

import com.university.library.entity.Book;
import com.university.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;  // ← این خط باید باشد!

@Service
@Transactional
public class BookService {
    
    @Autowired
    private BookRepository bookRepository;
    
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }
    
    public Book addBook(String title, String author, Integer publicationYear) {
        Book book = new Book(title, author, publicationYear);
        return bookRepository.save(book);
    }
    
    public List<Book> searchBooks(String title, String author, Integer publicationYear) {
        return bookRepository.searchBooks(title, author, publicationYear);
    }
    
    public List<Book> searchBooksByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }
    
    public long getTotalBooksCount() {
        return bookRepository.count();
    }
    
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
    
    public Optional<Book> findById(Long id) {  // ← اینجا Optional استفاده می‌شود
        return bookRepository.findById(id);
    }
    
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }
    
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
    
    public List<Book> getAvailableBooks() {
        return bookRepository.findByAvailableTrue();
    }
}