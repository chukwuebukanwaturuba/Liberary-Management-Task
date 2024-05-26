package com.ebuka.librarymanagementsystem.controller;

import com.ebuka.librarymanagementsystem.dtos.response.BorrowBookResponse;
import com.ebuka.librarymanagementsystem.exception.LibraryManagementException;
import com.ebuka.librarymanagementsystem.model.entities.BorrowBook;
import com.ebuka.librarymanagementsystem.service.BorrowBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/borrow")
public class BorrowBookController {
    private final BorrowBookService borrowBookService;

    @PostMapping("/book")
    public ResponseEntity<BorrowBookResponse> borrowBook(@RequestParam Long bookId) {
        try {
            BorrowBookResponse borrowedBook = borrowBookService.borrow(bookId);
            return new ResponseEntity<>(borrowedBook, HttpStatus.CREATED);
        } catch (LibraryManagementException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/return/{bookId}")
    public ResponseEntity<BorrowBookResponse> returnBook(@PathVariable Long bookId) {
        BorrowBookResponse returnedBook = borrowBookService.returnBook(bookId);
        return ResponseEntity.ok(returnedBook);
    }
}
