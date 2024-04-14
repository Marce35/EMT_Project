package com.example.eshop.labs_project.service;

import com.example.eshop.labs_project.model.Author;
import com.example.eshop.labs_project.model.Book;
import com.example.eshop.labs_project.model.dto.BookDTO;
import com.example.eshop.labs_project.model.enumerations.Category;
import org.apache.coyote.BadRequestException;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> findAll();

    Optional<Book> findById(Long id);

    Optional<Book> findByName(String name);

    Book save(String name, Category category, Long author, Integer availableCopies);

    Optional<Book> save(BookDTO bookDTO);

    Optional<Book> edit(Long id, BookDTO bookDTO);

    Book edit(Long id, String name, Category category, Long author, Integer availableCopies);

    void deleteById(Long id);

    Book markAsBorrowed(Long id) throws BadRequestException;

    Category mapStringToCategory(String bookCategory);

    Author editAuthor(Long id, String name, String surname, String country, String continent);

    Author saveAuthor(String name, String surname, String country, String continent);

    List<Category> getAllCategories();
}
