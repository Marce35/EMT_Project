package com.example.eshop.labs_project.controller;

import com.example.eshop.labs_project.model.Author;
import com.example.eshop.labs_project.model.Book;
import com.example.eshop.labs_project.model.Country;
import com.example.eshop.labs_project.model.dto.BookDTO;
import com.example.eshop.labs_project.model.enumerations.Category;
import com.example.eshop.labs_project.model.exceptions.BookNotFoundException;
import com.example.eshop.labs_project.service.BookService;
import org.apache.coyote.BadRequestException;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/books")
public class BookRestController {

    private final BookService bookService;

    public BookRestController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.findAll();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAllCategories(){
        List<Category> categories = bookService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/authors")
    public ResponseEntity<List<Author>> getAllAuthors(){
        List<Author> authors = bookService.getAllAuthors();
        return ResponseEntity.ok(authors);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Book> findBookById(@PathVariable Long id) {

        return this.bookService.findById(id)
                .map(book -> ResponseEntity.ok().body(book))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/findByName")
    public ResponseEntity<Book> findBookByName(@RequestParam String name) {
        return this.bookService.findByName(name)
                .map(book -> ResponseEntity.ok().body(book))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

//    @PostMapping("/add")
//    public ResponseEntity<Book> saveBook(@RequestParam(required = false) Long id,
//                           @RequestParam String name,
//                           @RequestParam String category,
//                           @RequestParam Long author,
//                           @RequestParam Integer availableCopies){
//        Category pickedCategory = this.bookService.mapStringToCategory(category);
//        if(id != null){
//            return ResponseEntity.ok(this.bookService.edit(id, name, pickedCategory, author, availableCopies));
//        }else{
//            return ResponseEntity.ok(this.bookService.save(name, pickedCategory, author, availableCopies));
//        }
//    }


    @PostMapping("/add")
    public ResponseEntity<Book> save(@RequestBody BookDTO bookDTO){
        return this.bookService.save(bookDTO)
                .map(book -> ResponseEntity.ok().body(book))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Book> edit(@PathVariable Long id, @RequestBody BookDTO bookDTO){
        return this.bookService.edit(id, bookDTO)
                .map(book -> ResponseEntity.ok().body(book))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteProduct(@PathVariable Long id) {
        this.bookService.deleteById(id);
        if(this.bookService.findById(id).isEmpty()){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
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
