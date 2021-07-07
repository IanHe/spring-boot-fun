package com.ian.controller;

import com.ian.domain.Book;
import com.ian.exceptions.BookNotFoundException;
import com.ian.exceptions.BookUnsupportedFieldPatchException;
import com.ian.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Map;

@Validated
@RestController
public class BookController {

    private BookRepository bookRepository;

    @Autowired
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/books")
    List<Book> getAll() {
        return this.bookRepository.findAll();
    }

    @PostMapping("/books")
    @ResponseStatus(HttpStatus.CREATED)
    Book newBook(@Valid @RequestBody Book newBook) {
        return this.bookRepository.save(newBook);
    }

    @GetMapping("/books/{id}")
    Book findOne(@PathVariable @Min(1) Long id) throws BookNotFoundException {
        return this.bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
    }

    @PutMapping("/books/{id}")
    Book saveOrUpdate(@RequestBody Book newBook, @PathVariable Long id) {
        return bookRepository.findById(id).map(book -> {
            book.setName(newBook.getName());
            book.setAuthor(newBook.getAuthor());
            book.setPrice(newBook.getPrice());
            return bookRepository.save(book);
        }).orElseGet(() -> {
            newBook.setId(id);
            return bookRepository.save(newBook);
        });
    }

    @PatchMapping("/books/{id}")
    Book patch(@RequestBody Map<String, String> update, @PathVariable Long id) {
        return bookRepository.findById(id)
                .map(x -> {
                    String author = update.get("author");
                    if (!StringUtils.isEmpty(author)) {
                        x.setAuthor(author);
                        return bookRepository.save(x);
                    }
                    throw new BookUnsupportedFieldPatchException(update.keySet());
                })
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    @DeleteMapping("/books/{id}")
    void deleteBook(@PathVariable Long id) {
        bookRepository.deleteById(id);
    }
}
