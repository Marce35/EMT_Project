package com.example.eshop.labs_project.service.Impl;

import com.example.eshop.labs_project.model.Author;
import com.example.eshop.labs_project.model.Book;
import com.example.eshop.labs_project.model.Country;
import com.example.eshop.labs_project.model.dto.BookDTO;
import com.example.eshop.labs_project.model.enumerations.Category;
import com.example.eshop.labs_project.model.exceptions.AuthorNotFoundException;
import com.example.eshop.labs_project.model.exceptions.BookNotFoundException;
import com.example.eshop.labs_project.repository.AuthorRepository;
import com.example.eshop.labs_project.repository.BookRepository;
import com.example.eshop.labs_project.repository.CountryRepository;
import com.example.eshop.labs_project.service.BookService;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CountryRepository countryRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, CountryRepository countryRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.countryRepository = countryRepository;
    }


    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public Optional<Book> findByName(String name) {
        return bookRepository.findByName(name);
    }

    @Override
    public Book save(String name, Category category, Long authorId, Integer availableCopies) {
        Author author = this.authorRepository.findById(authorId).orElseThrow(() -> new AuthorNotFoundException(authorId));

        this.bookRepository.deleteByName(name);
        Book book = new Book(name, category, author, availableCopies);
        this.bookRepository.save(book);

        return book;

    }

    @Override
    public Optional<Book> save(BookDTO bookDTO) {
        Category category = this.mapStringToCategory(bookDTO.getCategory());
        Author author = this.authorRepository.findById(bookDTO.getAuthorId()).orElseThrow(() -> new AuthorNotFoundException(bookDTO.getAuthorId()));

        this.bookRepository.deleteByName(bookDTO.getName());
        Book book = new Book(bookDTO.getName(), category, author, bookDTO.getAvailableCopies());
        this.bookRepository.save(book);

        return Optional.of(book);
    }

    @Override
    public Optional<Book> edit(Long id, BookDTO bookDTO) {
        Book book = this.bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));

        book.setName(bookDTO.getName());
        book.setAvailableCopies(bookDTO.getAvailableCopies());

        Category category = this.mapStringToCategory(bookDTO.getCategory());
        Author author = this.authorRepository.findById(bookDTO.getAuthorId()).orElseThrow(() -> new AuthorNotFoundException(bookDTO.getAuthorId()));

        book.setAuthor(author);
        book.setCategory(category);

        this.bookRepository.save(book);

        return Optional.of(book);
    }

    @Override
    public Book edit(Long id, String name, Category category, Long authorId, Integer availableCopies) {
        Book book = this.bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));

        Author newAuthor = this.authorRepository.findById(authorId).orElseThrow(() -> new AuthorNotFoundException(authorId));

        book.setName(name);
        book.setCategory(category);
        book.setAuthor(newAuthor);
        book.setAvailableCopies(availableCopies);

        this.bookRepository.save(book);
        return book;
    }

    @Override
    public void deleteById(Long id) {
        this.bookRepository.deleteById(id);
    }

    @Override
    public Book markAsBorrowed(Long id) throws BadRequestException {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        int availableCopies = book.getAvailableCopies();
        if (availableCopies > 0) {
            book.setAvailableCopies(availableCopies - 1);
            return bookRepository.save(book);
        } else {
            throw new BadRequestException("No available copies");
        }
    }

    @Override
    public Category mapStringToCategory(String bookCategory) {
        try {
            return Category.valueOf(bookCategory.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            // Handle the case where the provided type doesn't match any enum value
            return null; // Or throw an exception, or use a default value
        }
    }

    @Override
    public Author saveAuthor(String name, String surname, String country, String continent) {
        Country createdCountry = new Country(country, continent);

        Author author = new Author(name, surname, createdCountry);
        authorRepository.save(author);
        return author;
    }

    @Override
    public Author editAuthor(Long id, String name, String surname, String country, String continent) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new AuthorNotFoundException(id));

        Country createdCountry = new Country(country, continent);

        author.setName(name);
        author.setSurname(surname);
        author.setCountry(createdCountry);

        this.authorRepository.save(author);
        return author;
    }


}
