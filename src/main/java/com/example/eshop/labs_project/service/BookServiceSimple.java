package com.example.eshop.labs_project.service;

import com.example.eshop.labs_project.model.Book;

public interface BookServiceSimple {
    Book addBook(Book book);
    void deleteBook(Long id);
    Book editBook(Long id, Book book);
    Book markAsBorrowed(Long id);
}
