package com.example.eshop.labs_project.controller;

import com.example.eshop.labs_project.model.Author;
import com.example.eshop.labs_project.model.Book;
import com.example.eshop.labs_project.model.Country;
import com.example.eshop.labs_project.model.enumerations.Category;
import com.example.eshop.labs_project.model.exceptions.BookNotFoundException;
import com.example.eshop.labs_project.service.BookService;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookRestController {

    private final BookService bookService;

    public BookRestController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.findAll();
        return ResponseEntity.ok(books);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Book> findBookById(@PathVariable Long id) {
        Book book = bookService.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        return ResponseEntity.ok(book);
    }

    @GetMapping("/findByName")
    public ResponseEntity<Book> findBookByName(@RequestParam String name) {
        Book book = bookService.findByName(name)
                .orElseThrow(() -> new BookNotFoundException(name));
        return ResponseEntity.ok(book);
    }

    @PostMapping("/add")
    public ResponseEntity<Book> saveBook(@RequestParam(required = false) Long id,
                           @RequestParam String name,
                           @RequestParam String category,
                           @RequestParam Long author,
                           @RequestParam Integer availableCopies){
        Category pickedCategory = this.bookService.mapStringToCategory(category);
        if(id != null){
            return ResponseEntity.ok(this.bookService.edit(id, name, pickedCategory, author, availableCopies));
        }else{
            return ResponseEntity.ok(this.bookService.save(name, pickedCategory, author, availableCopies));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        this.bookService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/borrow")
    public ResponseEntity<Book> markAsBorrowed(@PathVariable Long id) throws BadRequestException {
        return ResponseEntity.ok(bookService.markAsBorrowed(id));
    }

//    id (Long), name (String), surname (String), country (Country).
    @PostMapping("/addAuthor")
    public ResponseEntity<Author> addAuthor(@RequestParam(required = false) Long id,
                                            @RequestParam String name,
                                            @RequestParam String surname,
                                            @RequestParam String country,
                                            @RequestParam String continent){
        if(id != null){
            return ResponseEntity.ok(this.bookService.editAuthor(id, name, surname, country, continent));
        }else{
            return ResponseEntity.ok(this.bookService.saveAuthor(name, surname, country, continent));
        }
    }

}
