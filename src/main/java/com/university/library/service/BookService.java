// file: src/main/java/com/university/library/service/BookService.java
package com.university.library.service;

import com.university.library.model.Book;
import java.util.ArrayList;
import java.util.List;

public class BookService {
    private List<Book> books;
    
    public BookService() {
        this.books = new ArrayList<>();
    }
    
    public void addBook(String title, String author, int publicationYear) {
        Book book = new Book(title, author, publicationYear);
        books.add(book);
    }
    
    public List<Book> searchBooks(String title, String author, Integer publicationYear) {
        List<Book> result = new ArrayList<>();
        
        for (Book book : books) {
            boolean matches = true;
            
            if (title != null && !title.isEmpty() && 
                !book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                matches = false;
            }
            
            if (author != null && !author.isEmpty() && 
                !book.getAuthor().toLowerCase().contains(author.toLowerCase())) {
                matches = false;
            }
            
            if (publicationYear != null && book.getPublicationYear() != publicationYear) {
                matches = false;
            }
            
            if (matches) {
                result.add(book);
            }
        }
        
        return result;
    }
    
    public List<Book> searchBooksByTitle(String title) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }
    
    public int getTotalBooksCount() {
        return books.size();
    }
    
    public List<Book> getBooks() {
        return new ArrayList<>(books);
    }
}