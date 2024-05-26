package com.ebuka.librarymanagementsystem.controller;

import com.ebuka.librarymanagementsystem.dtos.request.BookUpdateRequest;
import com.ebuka.librarymanagementsystem.dtos.request.NewBookRequest;
import com.ebuka.librarymanagementsystem.dtos.response.BookResponse;
import com.ebuka.librarymanagementsystem.model.entities.Book;
import com.ebuka.librarymanagementsystem.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/book")
public class BookController {

    private final BookService bookService;


    @PostMapping("/add")
    public ResponseEntity<?> addBook(@RequestBody @Valid NewBookRequest request) {
        try {
            ResponseEntity<?> response = bookService.addBook(request);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding book: " + e.getMessage());
        }

    }

    @GetMapping("/all-books")
    public ResponseEntity<List<Book>> getAllBooks() {
        try {
            List<Book> books = bookService.getAllBooks();
            if (books.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(books, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-book/{id}")
    public BookResponse getBookById(@PathVariable Long id) {

        return bookService.getBookById(id);
    }

    @PutMapping("/updateBook/{id}")
    public ResponseEntity<BookResponse> updatePatronInfo(@PathVariable Long id, @RequestBody BookUpdateRequest request) {
        BookResponse response = bookService.updateBookInfo(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deleteBook/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        try {
            String result = bookService.deleteBookById(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>("Sorry book does not exist with the id: "+ id, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
